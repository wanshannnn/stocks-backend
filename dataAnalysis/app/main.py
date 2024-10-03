from fastapi import FastAPI, Query
from . import service
import logging

logger = logging.getLogger(__name__)

app = FastAPI()

@app.get("/")
def root():
    return {"message": "FastAPI is running"}

@app.get("/analysis/id/{id}")
async def get_stock_analysis_by_id(
        id: str,
        start_time: int = Query(202401010101),
        end_time: int = Query(202412312359)):
    logger.info(f"Received request with id: {id}, start_time: {start_time}, end_time: {end_time}")
    analysis_result = await service.StockData.get_stock_analysis_by_id(id, start_time, end_time)
    logger.info(f"Returning analysis result for id: {id}")
    return analysis_result

@app.get("/analysis/name/{name}")
async def get_stock_analysis_by_name(
        name: str,
        start_time: int = Query(202401010101),
        end_time: int = Query(202412312359)):
    logger.info(f"Received request with name: {name}, start_time: {start_time}, end_time: {end_time}")
    analysis_result = await service.StockData.get_stock_analysis_by_name(name, start_time, end_time)
    logger.info(f"Returning analysis result for name: {name}")
    return analysis_result

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="127.0.0.1", port=8082)