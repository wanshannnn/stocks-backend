package st.backend.stocks.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import st.backend.stocks.dto.StockDataDTO;

import java.util.List;

public interface StockDataService {

    StockDataDTO getLatestStockDataById(String id);

    StockDataDTO getLatestStockDataByName(String name);

    Page<StockDataDTO> getDataByIdAndTimeRange(
            String id,
            long startTime,
            long endTime,
            Pageable pageable);

    Page<StockDataDTO> getDataByNameAndTimeRange(
            String name,
            long startTime,
            long endTime,
            Pageable pageable);

    void deleteStockDataByIdAndTimeRange(String id, Long startTime, Long endTime);

    void deleteStockDataByNameAndTimeRange(String name, Long startTime, Long endTime);

    StockDataDTO addStockData(StockDataDTO stockDataDTO);

    List<StockDataDTO> updateStockDataById(String id, StockDataDTO stockDataDTO);

    List<StockDataDTO> updateStockDataByName(String name, StockDataDTO stockDataDTO);

}
