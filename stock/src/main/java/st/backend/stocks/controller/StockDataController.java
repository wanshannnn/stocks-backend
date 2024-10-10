package st.backend.stocks.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import st.backend.stocks.Response;
import st.backend.stocks.dto.StockDataDTO;
import st.backend.stocks.service.StockDataService;

import java.util.List;

import static st.backend.stocks.Response.*;

@RestController
@Slf4j
@RequestMapping("/stocks")
public class StockDataController {

    @Autowired
    private StockDataService stockDataService;

    /**
     *
     * @param id 对应于股票代码
     * @return 返回股票的最新数据
     */
    @GetMapping("/id/{id}/latest")
    public Response<StockDataDTO> getLatestStockDataById(@PathVariable String id) {
        StockDataDTO data = stockDataService.getLatestStockDataById(id);
        if (data != null) {
            return newSuccess(data);
        } else {
            return newFail("Stock not found with ID: " + id, 404);
        }
    }

    /**
     *
     * @param name 对应于股票名称
     * @return 返回股票的最新数据
     */
    @GetMapping("/name/{name}/latest")
    public Response<StockDataDTO> getLatestStockDataByName(@PathVariable String name) {
        StockDataDTO data =  stockDataService.getLatestStockDataByName(name);
        if (data != null) {
            return newSuccess(data);
        } else {
            return newFail("Stock not found with name: " + name, 404);
        }
    }

    /**
     *
     * @param id 对应于股票代码
     * @param startTime 对应于查询的开始时间，默认为2024-01-01 01:01:00
     * @param endTime 对应于查询的结束时间，默认为2024-12-31 23:59:00
     * @return 返回股票在查询时间内的全部数据
     */
    @GetMapping("/id/{id}/query")
    public Response<List<StockDataDTO>> getStockDataByIdAndTimeRange(
            @PathVariable String id,
            @RequestParam(defaultValue = "202401010101") Long startTime,
            @RequestParam(defaultValue = "202412312359") Long endTime) {
        if (startTime == null || endTime == null) {
            return Response.newFail("Missing required parameters: startTime and/or endTime", 400);
        }

        List<StockDataDTO> data = stockDataService.getStockDataByIdAndTimeRange(id, startTime, endTime);

        if (data == null || data.isEmpty()) {
            return Response.newFail("No data found for stock ID: " + id + " within the specified time range", 404);
        } else {
            return Response.newSuccess(data);
        }
    }

    /**
     *
     * @param name 对应于股票名称
     * @param startTime 对应于查询的开始时间，默认为2024-01-01 01:01:00
     * @param endTime 对应于查询的结束时间，默认为2024-12-31 23:59:00
     * @return 返回股票在查询时间内的全部数据
     */
    @GetMapping("/name/{name}/query")
    public Response<List<StockDataDTO>> getStockDataByNameAndTimeRange(
            @PathVariable String name,
            @RequestParam(defaultValue = "202401010101") Long startTime,
            @RequestParam(defaultValue = "202412312359") Long endTime) {
        if (startTime == null || endTime == null) {
            return Response.newFail("Missing required parameters: startTime and/or endTime", 400);
        }

        List<StockDataDTO> data = stockDataService.getStockDataByNameAndTimeRange(name, startTime, endTime);

        if (data == null || data.isEmpty()) {
            return Response.newFail("No data found for stock name: " + name + " within the specified time range", 404);
        } else {
            return Response.newSuccess(data);
        }
    }

    /**
     *
     * @param id 对应于股票代码
     * @param startTime 对应于查询的开始时间，默认为2024-01-01 01:01:00
     * @param endTime 对应于查询的结束时间，默认为2024-12-31 23:59:00
     * @param page 对应于查询的页数，默认为0
     * @param size 对应于每页的数据数量，默认一页返回20条数据
     * @return 返回股票在查询时期内的全部数据，按页返回
     */
    @GetMapping("/id/{id}/query/page")
    public Response<Page<StockDataDTO>> getStockDataByIdAndTimeRangeByPage (
            @PathVariable String id,
            @RequestParam(defaultValue = "202401010101") Long startTime,
            @RequestParam(defaultValue = "202412312359") Long endTime,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        if (startTime == null || endTime == null) {
            return Response.newFail("Missing required parameters: startTime and/or endTime", 400);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("beijingTime").ascending());
        Page<StockDataDTO> data = stockDataService.getStockDataByIdAndTimeRangeByPage(id, startTime, endTime, pageable);

        if (data == null || data.isEmpty()) {
            return Response.newFail("No data found for stock ID: " + id + " within the specified time range", 404);
        } else {
            return Response.newSuccess(data);
        }
    }

    /**
     *
     * @param name 对应于股票名称
     * @param startTime 对应于查询的开始时间，默认为2024-01-01 01:01:00
     * @param endTime 对应于查询的结束时间，默认为2024-12-31 23:59:00
     * @param page 对应于查询的页数，默认为0
     * @param size 对应于每页的数据数量，默认一页返回20条数据
     * @return 返回股票在查询时期内的全部数据，按页返回
     */
    @GetMapping("/name/{name}/query/page")
    public Response<Page<StockDataDTO>> getStockDataByNameAndTimeRangeByPage(
            @PathVariable String name,
            @RequestParam(defaultValue = "202401010101") Long startTime,
            @RequestParam(defaultValue = "202412312359") Long endTime,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        if (startTime == null || endTime == null) {
            return Response.newFail("Missing required parameters: startTime and/or endTime", 400);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("beijingTime").ascending());
        Page<StockDataDTO> data = stockDataService.getStockDataByNameAndTimeRangeByPage(name, startTime, endTime, pageable);

        if (data == null || data.isEmpty()) {
            return Response.newFail("No data found for stock name: " + name + " within the specified time range", 404);
        } else {
            return Response.newSuccess(data);
        }
    }

    /**
     * 根据 id 与起止时间删除股票数据，如果没有提供时间，则默认删除股票的全部数据
     */
    @DeleteMapping("/id/{id}/delete")
    public void deleteStockDataByIdAndTimeRange(
            @PathVariable String id,
            @RequestParam(required = false) Long startTime,
            @RequestParam(required = false) Long endTime) {

        // 如果未提供 startTime 和 endTime，则删除该股票 id 的所有数据
        if (startTime == null || endTime == null) {
            stockDataService.deleteAllStockDataById(id);
        } else {
            // 提供了 startTime 和 endTime，删除时间范围内的数据
            stockDataService.deleteStockDataByIdAndTimeRange(id, startTime, endTime);
        }
    }

    /**
     * 根据 name 与起止时间删除股票数据，如果没有提供时间，则默认删除股票的全部数据
     */
    @DeleteMapping("/name/{name}/delete")
    public void deleteStockDataByNameAndTimeRange(
            @PathVariable String name,
            @RequestParam(required = false) Long startTime,
            @RequestParam(required = false) Long endTime) {

        // 如果未提供 startTime 和 endTime，则删除该股票 name 的所有数据
        if (startTime == null || endTime == null) {
            stockDataService.deleteAllStockDataByName(name);
        } else {
            // 提供了 startTime 和 endTime，删除时间范围内的数据
            stockDataService.deleteStockDataByNameAndTimeRange(name, startTime, endTime);
        }
    }

    /**
     * 增加股票数据
     */
    @PostMapping("/add")
    public Response<StockDataDTO> addStockData(@RequestBody StockDataDTO stockDataDTO) {
        return Response.newSuccess(stockDataService.addStockData(stockDataDTO));
    }

    /**
     * 根据 id 更新股票数据
     */
    @PutMapping("/id/{id}/update")
    public Response<List<StockDataDTO>> updateStockDataById(@PathVariable String id, @RequestBody StockDataDTO stockDataDTO) {
        List<StockDataDTO> updatedResults = stockDataService.updateStockDataById(id, stockDataDTO);
        return Response.newSuccess(updatedResults);
    }

    /**
     * 根据 name 更新股票数据
     */
    @PutMapping("/name/{name}/update")
    public Response<List<StockDataDTO>> updateStockDataByName(@PathVariable String name, @RequestBody StockDataDTO stockDataDTO) {
        List<StockDataDTO> updatedResults = stockDataService.updateStockDataByName(name, stockDataDTO);
        return Response.newSuccess(updatedResults);
    }

}