# 使用官方 OpenJDK 8 镜像作为基础镜像
FROM openjdk:8

# 设置工作目录
WORKDIR /app

# 替换为国内的 Debian 镜像源
RUN sed -i 's/deb.debian.org/mirrors.aliyun.com/g' /etc/apt/sources.list

# 安装所需的依赖库
RUN apt-get update && \
    apt-get install -y \
    wget \
    libstdc++6 \
    libx11-6 \
    libx11-dev \
    libxcb1 \
    libxcomposite1 \
    libxdamage1 \
    libxext6 \
    libxfixes3 \
    libxi6 \
    libxrandr2 \
    libxrender1 \
    libxss1 \
    libxtst6 \
    libglib2.0-0 \
    libnss3

# 安装特定版本的 Chrome 浏览器
ARG CHROME_VERSION="127.0.6533.72"
RUN wget --no-verbose -O /tmp/chrome.deb https://dl.google.com/linux/chrome/deb/pool/main/g/google-chrome-stable/google-chrome-stable_${CHROME_VERSION}-1_amd64.deb \
  && apt install -y /tmp/chrome.deb \
  && rm /tmp/chrome.deb

# 设置环境变量
ENV RUNNING_IN_DOCKER=true
ENV CHROME_DRIVER_DOCKER_PATH=/opt/selenium/chromedriver-127.0.6533.72

# 将项目的jar包复制到 /app 目录下
COPY /webCrawler.jar /app/webCrawler.jar

# 暴露 Spring Boot 应用程序的端口
EXPOSE 8080

# 设置容器启动时运行的命令
CMD ["java", "-jar", "/app/webCrawler.jar"]
