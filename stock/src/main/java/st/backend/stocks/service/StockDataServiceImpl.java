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
import st.backend.stocks.Response;
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

    // 创建日志记录器
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
        long total = mongoTemplate.count(query, "stock_data");
        logger.info("已查找到id: {} 的 {} 条数据", id, total);

        List<StockData> results = mongoTemplate.find(query, StockData.class, "stock_data");
        List<StockDataDTO> dataDTOs = results.stream()
                .map(StockDataConverter::converterStockData)
                .collect(Collectors.toList());
        logger.info("已查找到id: {} 在时间 {} 到 {} 内的数据", id, startTime, endTime);

        return dataDTOs;

    }


    @Override
    public List<StockDataDTO> getStockDataByNameAndTimeRange(String name, long startTime, long endTime) {

        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name))
                .addCriteria(Criteria.where("beijingTime").gt(startTime).lt(endTime))
                .with(Sort.by(Sort.Direction.ASC, "beijingTime"));
        long total = mongoTemplate.count(query, "stock_data");
        logger.info("已查找到name: {} 的 {} 条数据", name, total);

        List<StockData> results = mongoTemplate.find(query, StockData.class, "stock_data");
        List<StockDataDTO> dataDTOs = results.stream()
                .map(StockDataConverter::converterStockData)
                .collect(Collectors.toList());
        logger.info("已查找到name: {} 在时间 {} 到 {} 内的数据", name, startTime, endTime);

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
        logger.info("已查找到id: {} 的 {} 条数据", id, total);

        List<StockData> results = mongoTemplate.find(query, StockData.class, "stock_data");
        List<StockDataDTO> dataDTOs = results.stream()
                .map(StockDataConverter::converterStockData)
                .collect(Collectors.toList());
        logger.info("已查找到id: {} 在时间 {} 到 {} 内的数据，并按页返回", id, startTime, endTime);

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
        logger.info("已查找到name: {} 的 {} 条数据", name, total);

        List<StockData> results = mongoTemplate.find(query, StockData.class, "stock_data");
        List<StockDataDTO> dataDTOs = results.stream()
                .map(StockDataConverter::converterStockData)
                .collect(Collectors.toList());
        logger.info("已查找到name: {} 在时间 {} 到 {} 内的数据，并按页返回", name, startTime, endTime);

        return new PageImpl<>(dataDTOs, pageable, total);

    }


    @Override
    public void deleteStockDataByIdAndTimeRange(String id, Long startTime, Long endTime) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id).and("beijingTime").gte(startTime).lte(endTime));
        mongoTemplate.remove(query, StockData.class, "stock_data");
        logger.info("已删除id: {} 在时间 {} 到 {} 内的数据", id, startTime, endTime);
    }


    @Override
    public void deleteAllStockDataById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        mongoTemplate.remove(query, StockData.class, "stock_data");
        logger.info("已删除id: {} 的全部数据", id);
    }


    @Override
    public void deleteStockDataByNameAndTimeRange(String name, Long startTime, Long endTime) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name).and("beijingTime").gte(startTime).lte(endTime));
        mongoTemplate.remove(query, StockData.class, "stock_data");
        logger.info("已删除name: {} 在时间 {} 到 {} 内的数据", name, startTime, endTime);
    }


    @Override
    public void deleteAllStockDataByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        mongoTemplate.remove(query, StockData.class, "stock_data");
        logger.info("已删除name: {} 的全部数据", name);
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
            logger.error("无法将 beijingTime: {} 转化为时间戳", beijingTimeString, e);
            return null;
        }
        StockData stockData = StockDataConverter.converterStockDataDTO(stockDataDTO);
        StockData savedResult = mongoTemplate.save(stockData, "stock_data");
        StockDataDTO savedDTO = StockDataConverter.converterStockData(savedResult);
        logger.info("成功将数据储存在 collection: stock_data");
        return savedDTO;
    }


    @Override
    public List<StockDataDTO> updateStockDataById(String id, StockDataDTO stockDataDTO) {

        Query query = new Query(Criteria.where("id").is(id)); // 查找所有具有相同 ID 的记录
        List<StockData> results = mongoTemplate.find(query, StockData.class, "stock_data");
        if (results.isEmpty()) {
            logger.warn("没有找到指定 ID 的记录: {}", id);
            return null;
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
        logger.info("已更新id: {} 的数据", id);
        return updatedResults;
    }


    @Override
    public List<StockDataDTO> updateStockDataByName(String name, StockDataDTO stockDataDTO) {

        Query query = new Query(Criteria.where("name").is(name)); // 查找所有具有相同 name 的记录
        List<StockData> results = mongoTemplate.find(query, StockData.class, "stock_data");
        if (results.isEmpty()) {
            logger.warn("没有找到指定 name 的记录: {}", name);
            return null;
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
        logger.info("已更新name: {} 的数据", name);
        return updatedResults;
    }

}
