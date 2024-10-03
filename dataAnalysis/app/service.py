from typing import Dict
from statistics import mean
import logging
from app import grpc_client


logger = logging.getLogger(__name__)

class StockData:

    @staticmethod
    async def get_stock_analysis_by_id(id, start_time, end_time) -> Dict[str, float]:

        # 实例化 gRPC 客户端
        grpc_client_instance = grpc_client.StockServiceClient()

        # 调用实例方法
        raw_data = grpc_client_instance.get_stock_data_by_id(id, start_time, end_time)
        logger.info("already get raw_ata from grpc")

        prices = [item.price for item in raw_data]
        volumes = [item.volume for item in raw_data]
        turnovers = [item.turnover for item in raw_data]

        cleaned_prices = []
        cleaned_volumes = []
        cleaned_turnovers = []
        for item in prices:
            if item != '-':
                item = float(item)
                cleaned_prices.append(item)
        for item in volumes:
            if item != '-':
                item = float(item)
                cleaned_volumes.append(item)
        for item in turnovers:
            if item != '-':
                item = float(item)
                cleaned_turnovers.append(item)

        analysis_result = {
            "average_price": mean(cleaned_prices),  # 价格平均值
            "average_volume": mean(cleaned_volumes),  # 交易量平均值
            "average_turnover": mean(cleaned_turnovers),  # 交易额平均值
            "total_volume": sum(cleaned_volumes),  # 交易量总数
            "total_turnover": sum(cleaned_turnovers),  # 交易额总数
            "highest_price": max(cleaned_prices),  # 最高价格
            "lowest_price": min(cleaned_prices)  # 最低价格
        }

        return analysis_result

    @staticmethod
    async def get_stock_analysis_by_name(name, start_time, end_time) -> Dict[str, float]:

        # 实例化 gRPC 客户端
        grpc_client_instance = grpc_client.StockServiceClient()

        # 调用实例方法
        raw_data = grpc_client_instance.get_stock_data_by_name(name, start_time, end_time)
        logger.info("get raw_ata for analysis")

        prices = [item.price for item in raw_data]
        volumes = [item.volume for item in raw_data]
        turnovers = [item.turnover for item in raw_data]

        cleaned_prices = []
        cleaned_volumes = []
        cleaned_turnovers = []
        for item in prices:
            if item != '-':
                item = float(item)
                cleaned_prices.append(item)
        for item in volumes:
            if item != '-':
                item = float(item)
                cleaned_volumes.append(item)
        for item in turnovers:
            if item != '-':
                item = float(item)
                cleaned_turnovers.append(item)

        analysis_result = {
            "average_price": mean(cleaned_prices),  # 价格平均值
            "average_volume": mean(cleaned_volumes),  # 交易量平均值
            "average_turnover": mean(cleaned_turnovers),  # 交易额平均值
            "total_volume": sum(cleaned_volumes),  # 交易量总数
            "total_turnover": sum(cleaned_turnovers),  # 交易额总数
            "highest_price": max(cleaned_prices),  # 最高价格
            "lowest_price": min(cleaned_prices)  # 最低价格
        }

        return analysis_result


