package st.backend.stocks.dao;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
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


}
