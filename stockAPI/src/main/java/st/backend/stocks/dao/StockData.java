package st.backend.stocks.dao;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "stock_data")
public class StockData {

    @Field("id")
    private String id; // 代码

    @Field("price")
    private String price; // 最新价

    @Field("exchange")
    private String exchange; // 涨跌幅的百分比

    @Field("turnover")
    private String turnover; // 成交额

    @Field("volume")
    private String volume; // 成交量

    @Field("amplitude")
    private String amplitude; // 振幅的百分比

    @Field("name")
    private String name; // 名称

    @Field("highest")
    private String highest; // 最高

    @Field("lowest")
    private String lowest; // 最低

    @Field("timestamp")
    private Date timestamp;

    @Field("beijingTime")
    private Long beijingTime;

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

}
