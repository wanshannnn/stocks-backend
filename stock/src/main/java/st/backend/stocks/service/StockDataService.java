package st.backend.stocks.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import st.backend.stocks.dto.StockDataDTO;

import java.util.List;

public interface StockDataService {

    StockDataDTO getLatestStockDataById(String id);

    StockDataDTO getLatestStockDataByName(String name);

    List<StockDataDTO> getAllStockDataById(String id);

    List<StockDataDTO> getAllStockDataByName(String name);

    List<StockDataDTO> getStockDataByIdAndTimeRange(
            String id,
            long startTime,
            long endTime);

    List<StockDataDTO> getStockDataByNameAndTimeRange(
            String name,
            long startTime,
            long endTime);

    Page<StockDataDTO> getAllStockDataByIdAndPage(
            String id,
            Pageable pageable);

    Page<StockDataDTO> getAllStockDataByNameAndPage(
            String name,
            Pageable pageable);

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

    void deleteAllStockDataById(String id);

    void deleteAllStockDataByName(String name);

    void deleteStockDataByIdAndTimeRange(String id, Long startTime, Long endTime);

    void deleteStockDataByNameAndTimeRange(String name, Long startTime, Long endTime);

    StockDataDTO addStockData(StockDataDTO stockDataDTO);

    List<StockDataDTO> updateStockDataById(String id, StockDataDTO stockDataDTO);

    List<StockDataDTO> updateStockDataByName(String name, StockDataDTO stockDataDTO);

}
