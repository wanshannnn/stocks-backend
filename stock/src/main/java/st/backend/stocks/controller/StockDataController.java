package st.backend.stocks.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/stocks")
public class StockDataController {

    @Autowired
    private StockDataService stockDataService;


    @Operation(summary = "获取最新的股票数据", description = "根据股票代码 id 获取该股票的最新数据")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功返回股票数据"),
            @ApiResponse(responseCode = "404", description = "未找到该股票代码 id 对应的数据")
    })
    @GetMapping("/id/{id}/latest")
    public Response<StockDataDTO> getLatestStockDataById(@PathVariable String id) {
        StockDataDTO data = stockDataService.getLatestStockDataById(id);
        if (data != null) {
            return newSuccess(data);
        } else {
            return newFail("Stock not found with ID: " + id, 404);
        }
    }


    @Operation(summary = "获取最新的股票数据", description = "根据股票名称 name 获取该股票的最新数据")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功返回股票数据"),
            @ApiResponse(responseCode = "404", description = "未找到该股票名称 name 对应的数据")
    })
    @GetMapping("/name/{name}/latest")
    public Response<StockDataDTO> getLatestStockDataByName(@PathVariable String name) {
        StockDataDTO data =  stockDataService.getLatestStockDataByName(name);
        if (data != null) {
            return newSuccess(data);
        } else {
            return newFail("Stock not found with name: " + name, 404);
        }
    }


    @Operation(summary = "获取股票数据", description = "根据股票代码 id 以及查询时间 startTime, endTime 获取该股票在查询时间内的数据。" +
            "如果未提供查询时间，默认返回该股票的所有数据。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功返回股票数据"),
            @ApiResponse(responseCode = "404", description = "未找到该股票代码 id 对应的数据")
    })
    @GetMapping("/id/{id}/query")
    public Response<List<StockDataDTO>> getStockDataByIdAndTimeRange(
            @PathVariable String id,
            @RequestParam(required = false) Long startTime,
            @RequestParam(required = false) Long endTime) {
        List<StockDataDTO> data;
        if (startTime == null || endTime == null) {
            data = stockDataService.getAllStockDataById(id);
        } else {
            data = stockDataService.getStockDataByIdAndTimeRange(id, startTime, endTime);
        }

        if (data == null || data.isEmpty()) {
            return Response.newFail("No data found for stock ID: " + id + " within the specified time range", 404);
        } else {
            return Response.newSuccess(data);
        }
    }


    @Operation(summary = "获取股票数据", description = "根据股票名称 name 以及查询时间 startTime, endTime 获取该股票在查询时间内的数据。" +
            "如果未提供查询时间，默认返回该股票的所有数据。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功返回股票数据"),
            @ApiResponse(responseCode = "404", description = "未找到该股票名称 name 对应的数据")
    })
    @GetMapping("/name/{name}/query")
    public Response<List<StockDataDTO>> getStockDataByNameAndTimeRange(
            @PathVariable String name,
            @RequestParam(required = false) Long startTime,
            @RequestParam(required = false) Long endTime) {
        List<StockDataDTO> data;
        if (startTime == null || endTime == null) {
            data = stockDataService.getAllStockDataByName(name);
        } else {
            data = stockDataService.getStockDataByNameAndTimeRange(name, startTime, endTime);
        }

        if (data == null || data.isEmpty()) {
            return Response.newFail("No data found for stock name: " + name + " within the specified time range", 404);
        } else {
            return Response.newSuccess(data);
        }
    }


    @Operation(summary = "按页获取股票数据", description = "根据股票代码 id 以及查询时间 startTime, endTime 获取该股票在查询时间内的数据，按页返回。" +
            "如果未提供查询时间，默认返回该股票的所有数据。" +
            "如未提供 返回页码 page 与 每页数据数量 size，默认返回第 0 页数据，每页 20 条。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功返回股票数据"),
            @ApiResponse(responseCode = "404", description = "未找到该股票代码 id 对应的数据")
    })
    @GetMapping("/id/{id}/query/page")
    public Response<Page<StockDataDTO>> getStockDataByIdAndTimeRangeByPage (
            @PathVariable String id,
            @RequestParam(required = false) Long startTime,
            @RequestParam(required = false) Long endTime,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("beijingTime").ascending());
        Page<StockDataDTO> data;

        if (startTime == null || endTime == null) {
            data = stockDataService.getAllStockDataByIdAndPage(id, pageable);
        } else {
            data = stockDataService.getStockDataByIdAndTimeRangeByPage(id, startTime, endTime, pageable);
        }

        if (data == null || data.isEmpty()) {
            return Response.newFail("No data found for stock ID: " + id + " within the specified time range", 404);
        } else {
            return Response.newSuccess(data);
        }
    }


    @Operation(summary = "按页获取股票数据", description = "根据股票名称 name 以及查询时间 startTime, endTime 获取该股票在查询时间内的数据，按页返回。" +
            "如果未提供查询时间，默认返回该股票的所有数据。" +
            "如未提供 返回页码 page 与 每页数据数量 size，默认返回第 0 页数据，每页 20 条。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功返回股票数据"),
            @ApiResponse(responseCode = "404", description = "未找到该股票名称 name 对应的数据")
    })
    @GetMapping("/name/{name}/query/page")
    public Response<Page<StockDataDTO>> getStockDataByNameAndTimeRangeByPage(
            @PathVariable String name,
            @RequestParam(required = false) Long startTime,
            @RequestParam(required = false) Long endTime,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("beijingTime").ascending());
        Page<StockDataDTO> data;

        if (startTime == null || endTime == null) {
            data = stockDataService.getAllStockDataByNameAndPage(name, pageable);
        } else {
            data = stockDataService.getStockDataByNameAndTimeRangeByPage(name, startTime, endTime, pageable);
        }

        if (data == null || data.isEmpty()) {
            return Response.newFail("No data found for stock name: " + name + " within the specified time range", 404);
        } else {
            return Response.newSuccess(data);
        }
    }


    @Operation(summary = "删除股票数据", description = "根据股票代码 id 以及查询时间 startTime, endTime 删除该股票在查询时间内的数据。" +
            "如果未提供查询时间，默认删除该股票的所有数据。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功删除股票数据"),
            @ApiResponse(responseCode = "404", description = "未找到该股票代码 id 对应的数据")
    })
    @DeleteMapping("/id/{id}/delete")
    public void deleteStockDataByIdAndTimeRange(
            @PathVariable String id,
            @RequestParam(required = false) Long startTime,
            @RequestParam(required = false) Long endTime) {

        if (startTime == null || endTime == null) {
            stockDataService.deleteAllStockDataById(id);
        } else {
            stockDataService.deleteStockDataByIdAndTimeRange(id, startTime, endTime);
        }
    }


    @Operation(summary = "删除股票数据", description = "根据股票名称 name 以及查询时间 startTime, endTime 删除该股票在查询时间内的数据。" +
            "如果未提供查询时间，默认删除该股票的所有数据。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功删除股票数据"),
            @ApiResponse(responseCode = "404", description = "未找到该股票名称 name 对应的数据")
    })
    @DeleteMapping("/name/{name}/delete")
    public void deleteStockDataByNameAndTimeRange(
            @PathVariable String name,
            @RequestParam(required = false) Long startTime,
            @RequestParam(required = false) Long endTime) {

        if (startTime == null || endTime == null) {
            stockDataService.deleteAllStockDataByName(name);
        } else {
            stockDataService.deleteStockDataByNameAndTimeRange(name, startTime, endTime);
        }
    }


    @Operation(summary = "增加股票数据")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功增加股票数据"),
            @ApiResponse(responseCode = "400", description = "json数据存在错误")
    })
    @PostMapping("/add")
    public Response<StockDataDTO> addStockData(@RequestBody StockDataDTO stockDataDTO) {
        return Response.newSuccess(stockDataService.addStockData(stockDataDTO));
    }


    @Operation(summary = "修改股票数据", description = "根据股票代码 id 修改该股票的数据。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功修改股票数据"),
            @ApiResponse(responseCode = "404", description = "未找到该股票代码 id 对应的数据")
    })
    @PutMapping("/id/{id}/update")
    public Response<List<StockDataDTO>> updateStockDataById(@PathVariable String id, @RequestBody StockDataDTO stockDataDTO) {
        List<StockDataDTO> updatedResults = stockDataService.updateStockDataById(id, stockDataDTO);
        return Response.newSuccess(updatedResults);
    }


    @Operation(summary = "修改股票数据", description = "根据股票名称 name 修改该股票的数据。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功修改股票数据"),
            @ApiResponse(responseCode = "404", description = "未找到该股票名称 name 对应的数据")
    })
    @PutMapping("/name/{name}/update")
    public Response<List<StockDataDTO>> updateStockDataByName(@PathVariable String name, @RequestBody StockDataDTO stockDataDTO) {
        List<StockDataDTO> updatedResults = stockDataService.updateStockDataByName(name, stockDataDTO);
        return Response.newSuccess(updatedResults);
    }

}