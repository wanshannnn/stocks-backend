package st.backend.stocks.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import st.backend.stocks.converter.StockDataConverter;
import st.backend.stocks.dao.StockData;
import st.backend.stocks.dto.StockDataDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
public class StockDataServiceImpl implements StockDataService {

    private static final Logger logger = LoggerFactory.getLogger(StockDataServiceImpl.class);

    // 构造器注入实现依赖注入
    private final MongoTemplate mongoTemplate;

    @Autowired
    public StockDataServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public StockDataDTO getLatestStockDataById(String id) {
        Query query = new Query()
                .addCriteria(Criteria.where("id").is(id))
                .with(Sort.by(Sort.Direction.DESC, "beijingTime"))
                .limit(1); // 只返回最新的一条记录
        StockData latestResult = mongoTemplate.findOne(query, StockData.class, "stock_data");
        if (latestResult != null) {
            logger.info("已查找到id: {} 的最新数据", id);
            return StockDataConverter.converterStockData(latestResult);
        } else {
            logger.warn("未检索到id: {}", id);
            return null;
        }
    }


    @Override
    public StockDataDTO getLatestStockDataByName(String name) {
        Query query = new Query()
                .addCriteria(Criteria.where("name").is(name))
                .with(Sort.by(Sort.Direction.DESC, "beijingTime"))
                .limit(1); // 只返回最新的一条记录
        StockData latestResult = mongoTemplate.findOne(query, StockData.class, "stock_data");
        if (latestResult != null) {
            logger.info("已查找到name: {} 的最新数据", name);
            return StockDataConverter.converterStockData(latestResult);
        } else {
            logger.warn("未检索到name: {}", name);
            return null;
        }
    }


    @Override
    public List<StockDataDTO> getStockDataByIdAndTimeRange(String id, long startTime, long endTime) {

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id))
                .addCriteria(Criteria.where("beijingTime").gt(startTime).lt(endTime))
                .with(Sort.by(Sort.Direction.ASC, "beijingTime"));

        List<StockData> results = mongoTemplate.find(query, StockData.class, "stock_data");

        List<StockDataDTO> dataDTOs = results.stream()
                .map(StockDataConverter::converterStockData)
                .collect(Collectors.toList());

        return dataDTOs;
    }


    @Override
    public List<StockDataDTO> getStockDataByNameAndTimeRange(String name, long startTime, long endTime) {

        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name))
                .addCriteria(Criteria.where("beijingTime").gt(startTime).lt(endTime))
                .with(Sort.by(Sort.Direction.ASC, "beijingTime"));

        List<StockData> results = mongoTemplate.find(query, StockData.class, "stock_data");

        List<StockDataDTO> dataDTOs = results.stream()
                .map(StockDataConverter::converterStockData)
                .collect(Collectors.toList());

        return dataDTOs;
    }


    @Cacheable(
            value = "dataCache",
            key = "'stock_' + #id + '_data_' + #startTime + '_' + #endTime + '_' + #pageable.pageNumber + '_' + #pageable.pageSize")
    public Page<StockDataDTO> getStockDataByIdAndTimeRangeByPage(String id, long startTime, long endTime, Pageable pageable) {

        Query query = new Query()
                .addCriteria(Criteria.where("id").is(id))
                .addCriteria(Criteria.where("beijingTime").gt(startTime).lt(endTime))
                .with(Sort.by("beijingTime").ascending())
                .with(pageable);
        long total = mongoTemplate.count(query, "stock_data");

        List<StockData> results = mongoTemplate.find(query, StockData.class, "stock_data");

        List<StockDataDTO> dataDTOs = results.stream()
                .map(StockDataConverter::converterStockData)
                .collect(Collectors.toList());
        return new PageImpl<>(dataDTOs, pageable, total);

    }


    @Cacheable(
            value = "dataCache",
            key = "'stock_' + #name + '_data_' + #startTime + '_' + #endTime + '_' + #pageable.pageNumber + '_' + #pageable.pageSize")
    public Page<StockDataDTO> getStockDataByNameAndTimeRangeByPage(String name, long startTime, long endTime, Pageable pageable) {

        Query query = new Query()
                .addCriteria(Criteria.where("name").is(name))
                .addCriteria(Criteria.where("beijingTime").gt(startTime).lt(endTime))
                .with(pageable);
        long total = mongoTemplate.count(query, "stock_data");

        List<StockData> results = mongoTemplate.find(query, StockData.class, "stock_data");

        List<StockDataDTO> dataDTOs = results.stream()
                .map(StockDataConverter::converterStockData)
                .collect(Collectors.toList());
        return new PageImpl<>(dataDTOs, pageable, total);

    }


    @Override
    public void deleteStockDataByIdAndTimeRange(String id, Long startTime, Long endTime) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id).and("beijingTime").gte(startTime).lte(endTime));
        mongoTemplate.remove(query, StockData.class, "stock_data");
    }


    @Override
    public void deleteAllStockDataById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        mongoTemplate.remove(query, StockData.class, "stock_data");
    }


    @Override
    public void deleteStockDataByNameAndTimeRange(String name, Long startTime, Long endTime) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name).and("beijingTime").gte(startTime).lte(endTime));
        mongoTemplate.remove(query, StockData.class, "stock_data");
    }


    @Override
    public void deleteAllStockDataByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        mongoTemplate.remove(query, StockData.class, "stock_data");
    }


    @Override
    public StockDataDTO addStockData(StockDataDTO stockDataDTO) {
        Long beijingTime = stockDataDTO.getBeijingTime();
        String beijingTimeString = String.valueOf(beijingTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        try {
            Date timestamp = sdf.parse(beijingTimeString);
            stockDataDTO.setTimestamp(timestamp);
        } catch (ParseException e) {
            // 打印错误信息
            System.err.println("ParseException: " + e.getMessage());
            e.printStackTrace();
            // 处理异常，例如返回 null 或抛出自定义异常
            return null;
        }
        StockData stockData = StockDataConverter.converterStockDataDTO(stockDataDTO);
        StockData savedResult = mongoTemplate.save(stockData, "stock_data");
        StockDataDTO savedDTO = StockDataConverter.converterStockData(savedResult);
        return savedDTO;
    }


    @Override
    public List<StockDataDTO> updateStockDataById(String id, StockDataDTO stockDataDTO) {
        // 查找所有具有相同 ID 的记录
        Query query = new Query(Criteria.where("id").is(id));
        List<StockData> results = mongoTemplate.find(query, StockData.class, "stock_data");
        if (results.isEmpty()) {
            throw new IllegalArgumentException("No records found with the specified ID.");
        }
        List<StockDataDTO> updatedResults = new ArrayList<>();
        for (StockData result : results) {
            // 更新非空字段
            if (stockDataDTO.getPrice() != null) {
                result.setPrice(stockDataDTO.getPrice());
            }
            if (stockDataDTO.getExchange() != null) {
                result.setExchange(stockDataDTO.getExchange());
            }
            if (stockDataDTO.getTurnover() != null) {
                result.setTurnover(stockDataDTO.getTurnover());
            }
            if (stockDataDTO.getVolume() != null) {
                result.setVolume(stockDataDTO.getVolume());
            }
            if (stockDataDTO.getAmplitude() != null) {
                result.setAmplitude(stockDataDTO.getAmplitude());
            }
            if (stockDataDTO.getHighest() != null) {
                result.setHighest(stockDataDTO.getHighest());
            }
            if (stockDataDTO.getLowest() != null) {
                result.setLowest(stockDataDTO.getLowest());
            }
            // 保留原有的 id、name 和 UTCTimeDate
            result.setId(result.getId());
            result.setName(result.getName());
            result.setBeijingTime(result.getBeijingTime());
            result.setTimestamp(result.getTimestamp());
            // 保存更新后的结果
            StockData savedResult = mongoTemplate.save(result, "stock_data");
            updatedResults.add(StockDataConverter.converterStockData(savedResult));
        }
        return updatedResults;
    }


    @Override
    public List<StockDataDTO> updateStockDataByName(String name, StockDataDTO stockDataDTO) {
        // 查找所有具有相同 ID 的记录
        Query query = new Query(Criteria.where("name").is(name));
        List<StockData> results = mongoTemplate.find(query, StockData.class, "stock_data");
        if (results.isEmpty()) {
            throw new IllegalArgumentException("No records found with the specified Name.");
        }
        List<StockDataDTO> updatedResults = new ArrayList<>();
        for (StockData result : results) {
            // 更新非空字段
            if (stockDataDTO.getPrice() != null) {
                result.setPrice(stockDataDTO.getPrice());
            }
            if (stockDataDTO.getExchange() != null) {
                result.setExchange(stockDataDTO.getExchange());
            }
            if (stockDataDTO.getTurnover() != null) {
                result.setTurnover(stockDataDTO.getTurnover());
            }
            if (stockDataDTO.getVolume() != null) {
                result.setVolume(stockDataDTO.getVolume());
            }
            if (stockDataDTO.getAmplitude() != null) {
                result.setAmplitude(stockDataDTO.getAmplitude());
            }
            if (stockDataDTO.getHighest() != null) {
                result.setHighest(stockDataDTO.getHighest());
            }
            if (stockDataDTO.getLowest() != null) {
                result.setLowest(stockDataDTO.getLowest());
            }
            // 保留原有的 id、name 和 UTCTimeDate
            result.setId(result.getId());
            result.setName(result.getName());
            result.setBeijingTime(result.getBeijingTime());
            result.setTimestamp(result.getTimestamp());
            // 保存更新后的结果
            StockData savedResult = mongoTemplate.save(result, "stock_data");
            updatedResults.add(StockDataConverter.converterStockData(savedResult));
        }
        return updatedResults;
    }

}
