package st.backend.stocks.grpc;

import net.devh.boot.grpc.server.service.GrpcService;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import st.backend.stocks.StockServiceGrpc;
import st.backend.stocks.StockServiceProto;
import st.backend.stocks.service.StockDataService;
import st.backend.stocks.dto.StockDataDTO;

import java.util.List;

@GrpcService
public class StockGrpcServiceImpl extends StockServiceGrpc.StockServiceImplBase {

    @Autowired
    private StockDataService stockDataService;

    @Override
    public void getStock1(StockServiceProto.StockRequest1 request, StreamObserver<StockServiceProto.StockResponse1> responseObserver) {
        // 1. 接受 client 提供的参数
        String id = request.getId();
        long startTime = request.getStartTime();
        long endTime = request.getEndTime();

        // 2. 业务处理 - 调用 Spring Boot 的 Service
        List<StockDataDTO> data = stockDataService.getStockDataByIdAndTimeRange(id, startTime, endTime);

        // 3. 构建 gRPC 响应
        StockServiceProto.StockResponse1.Builder responseBuilder = StockServiceProto.StockResponse1.newBuilder();

        if (data == null || data.isEmpty()) {
            responseBuilder.setMessage("No data found for stock ID: " + id + " within the specified time range");
        } else {
            for (StockDataDTO stockDataDTO : data) {
                // 构建 StockResponseData 对象
                StockServiceProto.StockResponseData.Builder dataBuilder = StockServiceProto.StockResponseData.newBuilder()
                        .setId(stockDataDTO.getId())
                        .setName(stockDataDTO.getName())
                        .setPrice(stockDataDTO.getPrice())
                        .setAmplitude(stockDataDTO.getAmplitude())
                        .setExchange(stockDataDTO.getExchange())
                        .setTurnover(stockDataDTO.getTurnover())
                        .setVolume(stockDataDTO.getVolume())
                        .setHighest(stockDataDTO.getHighest())
                        .setLowest(stockDataDTO.getLowest())
                        .setTimestamp(stockDataDTO.getTimestamp().toString());

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

        // 2. 业务处理 - 调用 Spring Boot 的 Service
        List<StockDataDTO> data = stockDataService.getStockDataByNameAndTimeRange(name, startTime, endTime);

        // 3. 构建 gRPC 响应
        StockServiceProto.StockResponse2.Builder responseBuilder = StockServiceProto.StockResponse2.newBuilder();

        if (data == null || data.isEmpty()) {
            responseBuilder.setMessage("No data found for stock name: " + name + " within the specified time range");
        } else {
            for (StockDataDTO stockDataDTO : data) {
                // 构建 StockResponseData 对象
                StockServiceProto.StockResponseData.Builder dataBuilder = StockServiceProto.StockResponseData.newBuilder()
                        .setId(stockDataDTO.getId())
                        .setName(stockDataDTO.getName())
                        .setPrice(stockDataDTO.getPrice())
                        .setAmplitude(stockDataDTO.getAmplitude())
                        .setExchange(stockDataDTO.getExchange())
                        .setTurnover(stockDataDTO.getTurnover())
                        .setVolume(stockDataDTO.getVolume())
                        .setHighest(stockDataDTO.getHighest())
                        .setLowest(stockDataDTO.getLowest())
                        .setTimestamp(stockDataDTO.getTimestamp().toString());

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
