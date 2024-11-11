package st.backend.stocks.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "StockData DTO")
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

}
