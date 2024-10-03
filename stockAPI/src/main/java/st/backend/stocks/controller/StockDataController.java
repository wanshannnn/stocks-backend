package st.backend.stocks.controller;

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
public class StockDataController {

    @Autowired
    private StockDataService stockDataService;



    // 根据股票代码 id 获取该股票的最新数据
    @GetMapping("/stocks/id/{id}/latest")
    public Response<StockDataDTO> getLatestStockDataById(@PathVariable String id) {
        StockDataDTO result = stockDataService.getLatestStockDataById(id);
        if (result != null) {
            return newSuccess(result);
        } else {
            return newFail("Stock not found with ID: " + id, 404);
        }
    }



    // 根据股票名称 name 获取该股票的最新数据
    @GetMapping("/stocks/name/{name}/latest")
    public Response<StockDataDTO> getLatestStockDataByName(@PathVariable String name) {
        StockDataDTO result =  stockDataService.getLatestStockDataByName(name);
        if (result != null) {
            return newSuccess(result);
        } else {
            return newFail("Stock not found with name: " + name, 404);
        }
    }



    // 根据股票代码 id 、起止时间 startTime、endTime 获取该股票指定时期内的数据
    @GetMapping("/stocks/id/{id}/changes")
    public Response<Page<StockDataDTO>> getStocksById(
            @PathVariable String id,
            @RequestParam Long startTime,
            @RequestParam Long endTime,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (startTime == null || endTime == null) {
            return Response.newFail("Missing required parameters: startTime and/or endTime", 400);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("beijingTime").ascending());
        Page<StockDataDTO> data = stockDataService.getDataByIdAndTimeRange(id, startTime, endTime, pageable);

        if (data == null || data.isEmpty()) {
            return Response.newFail("No data found for stock ID: " + id + " within the specified time range", 404);
        } else {
            return Response.newSuccess(data);
        }
    }



    // 根据股票名称 name 、起止时间 startTime、endTime 获取该股票在指定时期内的数据
    @GetMapping("/stocks/name/{name}/changes")
    public Response<Page<StockDataDTO>> getStocksByName(
            @PathVariable String name,
            @RequestParam Long startTime,
            @RequestParam Long endTime,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (startTime == null || endTime == null) {
            return Response.newFail("Missing required parameters: startTime and/or endTime", 400);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("beijingTime").ascending());
        Page<StockDataDTO> data = stockDataService.getDataByNameAndTimeRange(name, startTime, endTime, pageable);

        if (data == null || data.isEmpty()) {
            return Response.newFail("No data found for stock name: " + name + " within the specified time range", 404);
        } else {
            return Response.newSuccess(data);
        }
    }



    // 按照 id 删除一定时间范围的股票数据
    @DeleteMapping("/stocks/id/{id}")
    public void deleteStockDataByIdAndTimeRange(
            @PathVariable String id,
            @RequestParam Long startTime,
            @RequestParam Long endTime) {
        stockDataService.deleteStockDataByIdAndTimeRange(id, startTime, endTime);
    }



    // 按照 name 删除一定时间范围的股票数据
    @DeleteMapping("/stocks/name/{name}")
    public void deleteStockDataByNameAndTimeRange(
            @PathVariable String name,
            @RequestParam Long startTime,
            @RequestParam Long endTime) {
        stockDataService.deleteStockDataByNameAndTimeRange(name, startTime, endTime);
    }



    // 增加股票数据
    @PostMapping("/stocks/")
    public Response<StockDataDTO> addStockData(@RequestBody StockDataDTO stockDataDTO) {
        return Response.newSuccess(stockDataService.addStockData(stockDataDTO));
    }



    // 根据 id 更改股票数据
    @PutMapping("/stocks/id/{id}")
    public Response<List<StockDataDTO>> updateStockDataById(@PathVariable String id, @RequestBody StockDataDTO stockDataDTO) {
        List<StockDataDTO> updatedResults = stockDataService.updateStockDataById(id, stockDataDTO);
        return Response.newSuccess(updatedResults);
    }



    // 根据 name 更改股票数据
    @PutMapping("/stocks/name/{name}")
    public Response<List<StockDataDTO>> updateStockDataByName(@PathVariable String name, @RequestBody StockDataDTO stockDataDTO) {
        List<StockDataDTO> updatedResults = stockDataService.updateStockDataByName(name, stockDataDTO);
        return Response.newSuccess(updatedResults);
    }



}