package st.backend.stocks.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import st.backend.stocks.dto.StockDataDTO;

import java.util.List;

public interface StockDataService {

    StockDataDTO getLatestStockDataById(String id);

    StockDataDTO getLatestStockDataByName(String name);

    List<StockDataDTO> getStockDataByIdAndTimeRange(
            String id,
            long startTime,
            long endTime);

    List<StockDataDTO> getStockDataByNameAndTimeRange(
            String name,
            long startTime,
            long endTime);

    Page<StockDataDTO> getStockDataByIdAndTimeRangeByPage(
            String id,
            long startTime,
            long endTime,
            Pageable pageable);

    Page<StockDataDTO> getStockDataByNameAndTimeRangeByPage(
            String name,
            long startTime,
            long endTime,
            Pageable pageable);

    void deleteStockDataByIdAndTimeRange(String id, Long startTime, Long endTime);

    void deleteAllStockDataById(String id);

    void deleteStockDataByNameAndTimeRange(String name, Long startTime, Long endTime);

    void deleteAllStockDataByName(String name);

    StockDataDTO addStockData(StockDataDTO stockDataDTO);

    List<StockDataDTO> updateStockDataById(String id, StockDataDTO stockDataDTO);

    List<StockDataDTO> updateStockDataByName(String name, StockDataDTO stockDataDTO);

}
