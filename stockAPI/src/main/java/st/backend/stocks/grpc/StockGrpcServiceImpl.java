package st.backend.stocks.grpc;

import net.devh.boot.grpc.server.service.GrpcService;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import st.backend.stocks.StockServiceGrpc;
import st.backend.stocks.StockServiceProto;
import st.backend.stocks.service.StockDataService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import st.backend.stocks.dto.StockDataDTO;

@GrpcService
public class StockGrpcServiceImpl extends StockServiceGrpc.StockServiceImplBase {

    @Autowired
    private StockDataService stockDataService;

    /*
    1. 接受 client 提供的参数 request.getParameter()
    2. 业务处理 service + dao 调用对应的业务功能
    3. 提供返回值
     */
    @Override
    public void getStock1(StockServiceProto.StockRequest1 request, StreamObserver<StockServiceProto.StockResponse1> responseObserver) {
        // 1. 接受 client 提供的参数
        String id = request.getId();
        long startTime = request.getStartTime();
        long endTime = request.getEndTime();
        int page = 0;
        int size = 10;

        // 2. 业务处理 - 调用 Spring Boot 的 Service
        Pageable pageable = PageRequest.of(page, size);
        Page<StockDataDTO> dataPage = stockDataService.getDataByIdAndTimeRange(id, startTime, endTime, pageable);

        // 3. 构建 gRPC 响应
        StockServiceProto.StockResponse1.Builder responseBuilder = StockServiceProto.StockResponse1.newBuilder();

        if (dataPage == null || dataPage.isEmpty()) {
            responseBuilder.setMessage("No data found for stock ID: " + id + " within the specified time range");
        } else {
            for (StockDataDTO stockData : dataPage.getContent()) {
                // 构建 StockResponseData 对象
                StockServiceProto.StockResponseData.Builder dataBuilder = StockServiceProto.StockResponseData.newBuilder()
                        .setId(stockData.getId())
                        .setName(stockData.getName())
                        .setPrice(stockData.getPrice())
                        .setAmplitude(stockData.getAmplitude())
                        .setExchange(stockData.getExchange())
                        .setTurnover(stockData.getTurnover())
                        .setVolume(stockData.getVolume())
                        .setHighest(stockData.getHighest())
                        .setLowest(stockData.getLowest())
                        .setTimestamp(stockData.getTimestamp().toString());

                // 将数据添加到响应中
                responseBuilder.addData(dataBuilder);
            }
            responseBuilder.setMessage("Success");
        }

        // 4. 提供返回值
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();

    }


    @Override
    public void getStock2(StockServiceProto.StockRequest2 request, StreamObserver<StockServiceProto.StockResponse2> responseObserver) {
        // 1. 接受 client 提供的参数
        String name = request.getName();
        long startTime = request.getStartTime();
        long endTime = request.getEndTime();
        int page = 0;
        int size = 10;

        // 2. 业务处理 - 调用 Spring Boot 的 Service
        Pageable pageable = PageRequest.of(page, size);
        Page<StockDataDTO> dataPage = stockDataService.getDataByNameAndTimeRange(name, startTime, endTime, pageable);

        // 3. 构建 gRPC 响应
        StockServiceProto.StockResponse2.Builder responseBuilder = StockServiceProto.StockResponse2.newBuilder();

        if (dataPage == null || dataPage.isEmpty()) {
            responseBuilder.setMessage("No data found for stock name: " + name + " within the specified time range");
        } else {
            for (StockDataDTO stockData : dataPage.getContent()) {
                // 构建 StockResponseData 对象
                StockServiceProto.StockResponseData.Builder dataBuilder = StockServiceProto.StockResponseData.newBuilder()
                        .setId(stockData.getId())
                        .setName(stockData.getName())
                        .setPrice(stockData.getPrice())
                        .setAmplitude(stockData.getAmplitude())
                        .setExchange(stockData.getExchange())
                        .setTurnover(stockData.getTurnover())
                        .setVolume(stockData.getVolume())
                        .setHighest(stockData.getHighest())
                        .setLowest(stockData.getLowest())
                        .setTimestamp(stockData.getTimestamp().toString());

                // 将数据添加到响应中
                responseBuilder.addData(dataBuilder);
            }
            responseBuilder.setMessage("Success");
        }

        // 4. 提供返回值
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();

    }
}
