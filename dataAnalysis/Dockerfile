# 使用官方轻量级 Python 镜像
FROM python:3.9-slim

# 设置工作目录
WORKDIR /app

# 复制项目的 requirements 文件（包含依赖包）
COPY ./requirements.txt /app/requirements.txt

# 安装依赖
RUN pip install -r /app/requirements.txt --timeout=1000 --retries=5 --index-url https://pypi.tuna.tsinghua.edu.cn/simple

# 复制 FastAPI 项目代码
COPY . /app

# 暴露端口
EXPOSE 8082

# 使用 Uvicorn 运行 FastAPI 应用
CMD ["uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "8082"]