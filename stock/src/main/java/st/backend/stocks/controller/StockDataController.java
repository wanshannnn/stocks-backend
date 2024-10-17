package st.backend.stocks.controller;

import io.swagger.annotations.*;
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

@Api(tags = "股票基本数据")
@RestController
@RequestMapping("/stocks")
public class StockDataController {

    @Autowired
    private StockDataService stockDataService;

    @ApiOperation("获取最新的股票数据")
    @ApiImplicitParam(name = "id", value = "股票代码", required = true)
    @GetMapping("/id/{id}/latest")
    public Response<StockDataDTO> getLatestStockDataById(@PathVariable String id) {
        StockDataDTO data = stockDataService.getLatestStockDataById(id);
        if (data != null) {
            return newSuccess(data);
        } else {
            return newFail("Stock not found with ID: " + id, 404);
        }
    }


    @ApiOperation(value = "获取最新的股票数据")
    @ApiImplicitParam(name = "id", value = "股票代码", required = true)
    @GetMapping("/name/{name}/latest")
    public Response<StockDataDTO> getLatestStockDataByName(@PathVariable String name) {
        StockDataDTO data =  stockDataService.getLatestStockDataByName(name);
        if (data != null) {
            return newSuccess(data);
        } else {
            return newFail("Stock not found with name: " + name, 404);
        }
    }


    @ApiOperation(value = "获取股票数据", notes = "根据股票代码 id 以及查询时间 startTime, endTime 获取该股票在查询时间内的数据。" +
            "如果未提供查询时间，是默认返回该股票的所有数据。")
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


    @ApiOperation(value = "获取股票数据", notes = "根据股票名称 name 以及查询时间 startTime, endTime 获取该股票在查询时间内的数据。" +
            "如果未提供查询时间，默认返回该股票的所有数据。")
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


    @ApiOperation(value = "按页获取股票数据", notes = "根据股票代码 id 以及查询时间 startTime, endTime 获取该股票在查询时间内的数据，按页返回。" +
            "如果未提供查询时间，默认返回该股票的所有数据。" +
            "如未提供 返回页码 page 与 每页数据数量 size，默认返回第 0 页数据，每页 20 条。")
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


    @ApiOperation(value = "按页获取股票数据", notes = "根据股票名称 name 以及查询时间 startTime, endTime 获取该股票在查询时间内的数据，按页返回。" +
            "如果未提供查询时间，默认返回该股票的所有数据。" +
            "如未提供 返回页码 page 与 每页数据数量 size，默认返回第 0 页数据，每页 20 条。")
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


    @ApiOperation(value = "删除股票数据", notes = "根据股票代码 id 以及查询时间 startTime, endTime 删除该股票在查询时间内的数据。" +
            "如果未提供查询时间，默认删除该股票的所有数据。")
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


    @ApiOperation(value = "删除股票数据", notes = "根据股票名称 name 以及查询时间 startTime, endTime 删除该股票在查询时间内的数据。" +
            "如果未提供查询时间，默认删除该股票的所有数据。")
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


    @ApiOperation(value = "增加股票数据")
    @PostMapping("/add")
    public Response<StockDataDTO> addStockData(@RequestBody StockDataDTO stockDataDTO) {
        return Response.newSuccess(stockDataService.addStockData(stockDataDTO));
    }


    @ApiOperation(value = "修改股票数据", notes = "根据股票代码 id 修改该股票的数据。")
    @PutMapping("/id/{id}/update")
    public Response<List<StockDataDTO>> updateStockDataById(@PathVariable String id, @RequestBody StockDataDTO stockDataDTO) {
        List<StockDataDTO> updatedResults = stockDataService.updateStockDataById(id, stockDataDTO);
        return Response.newSuccess(updatedResults);
    }


    @ApiOperation(value = "修改股票数据", notes = "根据股票名称 name 修改该股票的数据。")
    @PutMapping("/name/{name}/update")
    public Response<List<StockDataDTO>> updateStockDataByName(@PathVariable String name, @RequestBody StockDataDTO stockDataDTO) {
        List<StockDataDTO> updatedResults = stockDataService.updateStockDataByName(name, stockDataDTO);
        return Response.newSuccess(updatedResults);
    }

}