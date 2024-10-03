import grpc
from app import stock_pb2_grpc
from app import stock_pb2
from app.stock_pb2_grpc import StockServiceStub
import logging

logger = logging.getLogger(__name__)


class StockServiceClient:
    def __init__(self):
        self.channel = grpc.insecure_channel('localhost:50051')
        self.stub = StockServiceStub(self.channel)

    def get_stock_data_by_id(self, id: str, start_time: int, end_time: int):
        logger.info("client already received request and begin to work")
        request = stock_pb2.StockRequest1(id=id, start_time=start_time, end_time=end_time)
        response = self.stub.GetStock1(request)
        logger.info(response.message)
        return response.data

    def get_stock_data_by_name(self, name: str, start_time: int, end_time: int):
        request = stock_pb2.StockRequest2(name=name, start_time=start_time, end_time=end_time)
        response = self.stub.GetStock2(request)
        logger.info(response.message)
        return response.data