package st.backend.stocks.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StockDataDTO {

    private Date timestamp;

    private Long beijingTime;

    private String id;

    private String price;

    private String exchange;

    private String turnover;

    private String volume;

    private String amplitude;

    private String name;

    private String highest;

    private String lowest;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Long getBeijingTime() {
        return beijingTime;
    }

    public void setBeijingTime(Long beijingTime) {
        this.beijingTime = beijingTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getTurnover() {
        return turnover;
    }

    public void setTurnover(String turnover) {
        this.turnover = turnover;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(String amplitude) {
        this.amplitude = amplitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHighest() {
        return highest;
    }

    public void setHighest(String highest) {
        this.highest = highest;
    }

    public String getLowest() {
        return lowest;
    }

    public void setLowest(String lowest) {
        this.lowest = lowest;
    }
}
