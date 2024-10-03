package st.backend.stocks.converter;

import org.springframework.stereotype.Component;
import st.backend.stocks.dao.StockData;
import st.backend.stocks.dto.StockDataDTO;

@Component
public class StockDataConverter {

    public static StockDataDTO converterStockData (StockData stockData) {
        StockDataDTO stockDataDTO = new StockDataDTO();
        stockDataDTO.setId(stockData.getId());
        stockDataDTO.setName(stockData.getName());
        stockDataDTO.setPrice(stockData.getPrice());
        stockDataDTO.setAmplitude(stockData.getAmplitude());
        stockDataDTO.setExchange(stockData.getExchange());
        stockDataDTO.setTurnover(stockData.getTurnover());
        stockDataDTO.setVolume(stockData.getVolume());
        stockDataDTO.setHighest(stockData.getHighest());
        stockDataDTO.setLowest(stockData.getLowest());
        stockDataDTO.setBeijingTime(stockData.getBeijingTime());
        stockDataDTO.setTimestamp(stockData.getTimestamp());
        return stockDataDTO;
    }

    public static StockData converterStockDataDTO (StockDataDTO stockDataDTO) {
        StockData stockData = new StockData();
        stockData.setId(stockDataDTO.getId());
        stockData.setName(stockDataDTO.getName());
        stockData.setPrice(stockDataDTO.getPrice());
        stockData.setAmplitude(stockDataDTO.getAmplitude());
        stockData.setExchange(stockDataDTO.getExchange());
        stockData.setTurnover(stockDataDTO.getTurnover());
        stockData.setVolume(stockDataDTO.getVolume());
        stockData.setHighest(stockDataDTO.getHighest());
        stockData.setLowest(stockDataDTO.getLowest());
        stockData.setBeijingTime(stockDataDTO.getBeijingTime());
        stockData.setTimestamp(stockDataDTO.getTimestamp());
        return stockData;
    }


}
