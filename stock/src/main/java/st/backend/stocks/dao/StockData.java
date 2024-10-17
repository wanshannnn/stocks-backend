package st.backend.stocks.dao;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@ApiModel(value = "StockData DAO")
@Document(collection = "stock_data")
public class StockData {

    @ApiModelProperty(value = "股票代码")
    @Field("id")
    private String id;

    @ApiModelProperty(value = "最新价")
    @Field("price")
    private String price;

    @ApiModelProperty(value = "涨跌幅（百分比）")
    @Field("exchange")
    private String exchange;

    @ApiModelProperty(value = "成交额")
    @Field("turnover")
    private String turnover;

    @ApiModelProperty(value = "成交量")
    @Field("volume")
    private String volume;

    @ApiModelProperty(value = "振幅（百分比）")
    @Field("amplitude")
    private String amplitude;

    @ApiModelProperty(value = "股票名称")
    @Field("name")
    private String name;

    @ApiModelProperty(value = "最高")
    @Field("highest")
    private String highest;

    @ApiModelProperty(value = "最低")
    @Field("lowest")
    private String lowest;

    @ApiModelProperty(value = "时间戳")
    @Field("timestamp")
    private Date timestamp;

    @ApiModelProperty(value = "格式化北京时间")
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
