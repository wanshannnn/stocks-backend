# 使用官方 OpenJDK 8 镜像作为基础镜像
FROM openjdk:8

# 设置工作目录
WORKDIR /app

# 将项目的jar包复制到 /app 目录下
COPY /stock.jar /app/stock.jar

# 暴露 Spring Boot 应用程序的端口
EXPOSE 8081 50051

# 设置容器启动时运行的命令
CMD ["java", "-jar", "/app/stock.jar"]
