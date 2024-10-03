package st.backend.webcrawler.crawler;

import com.google.gson.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.net.URIBuilder;
import org.bson.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import st.backend.webcrawler.config.SeleniumConfig;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WebCrawlerService {

    private static final Logger logger = LoggerFactory.getLogger(WebCrawlerService.class);
    private final SeleniumConfig seleniumConfig;
    private final MongoClient mongoClient;
    private final Gson gson;

    @Autowired
    public WebCrawlerService(SeleniumConfig seleniumConfig, MongoClient mongoClient) {
        this.seleniumConfig = seleniumConfig;
        this.mongoClient = mongoClient;
        this.gson = new GsonBuilder().setLenient().create();
    }

    @Scheduled(fixedRate = 3600000) // 每小时执行一次爬虫程序
    public void crawlData() {

        String chromeDriverPath = System.getenv("RUNNING_IN_DOCKER") != null ?
                seleniumConfig.getDockerChromeDriverPath() :
                seleniumConfig.getLocalChromeDriverPath();

        System.setProperty("webdriver.chrome.driver", chromeDriverPath);

        ChromeOptions options = new ChromeOptions();
        if (System.getenv("RUNNING_IN_DOCKER") != null) {
            options.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage");
        }

        WebDriver driver = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
            driver = new ChromeDriver(options);
            logger.info("开始本次数据爬取");
            driver.get("https://quote.eastmoney.com/center/gridlist.html");

            String baseUrl = "https://28.push2.eastmoney.com/api/qt/clist/get";
            int totalPages = 282;
            int pz = 20;

            for (int page = 1; page <= totalPages; page++) {
                Map<String, Object> result = buildUri(baseUrl, page, pz);
                URI uri = (URI) result.get("uri");
                long timestamp = (Long) result.get("timestamp");
                try (CloseableHttpResponse response = httpclient.execute(new HttpGet(uri))) {
                    if (response.getCode() == 200) {
                        String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                        storeDataInMongo(content, timestamp);
                        logger.info("已将第 " + page + " 页的股票数据写入 Mongodb");
                    }
                } catch (IOException | ParseException e) {
                    logger.error("Error during HTTP request for page " + page, e);
                }
            }
        } catch (Exception e) {
            logger.error("Crawling process encountered an error", e);
        } finally {
            if (driver != null) {
                driver.quit();
            }
            logger.info("本次数据爬取已完成");
        }
    }

    public Map<String, Object> buildUri(String baseUrl, int page, int pz) throws URISyntaxException {
        Random random = new Random();
        String randomDigits = String.format("%017d", Math.abs(random.nextLong()) % 100000000000000000L);
        long timestamp = System.currentTimeMillis();

        URIBuilder uriBuilder = new URIBuilder(baseUrl);
        uriBuilder.addParameter("cb", "jQuery1124" + randomDigits + "_" + timestamp);
        uriBuilder.addParameter("pn", String.valueOf(page));
        uriBuilder.addParameter("pz", String.valueOf(pz));
        uriBuilder.addParameter("po", "1");
        uriBuilder.addParameter("np", "1");
        uriBuilder.addParameter("ut", "bd1d9ddb04089700cf9c27f6f7426281");
        uriBuilder.addParameter("fltt", "2");
        uriBuilder.addParameter("invt", "2");
        uriBuilder.addParameter("dect", "1");
        uriBuilder.addParameter("wbp2u", "|0|0|0|web");
        uriBuilder.addParameter("fid", "f3");
        uriBuilder.addParameter("fs", "m:0 t:6,m:0 t:80");
        uriBuilder.addParameter("fields", "f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f12,f13,f14,f15,f16,f17,f18,f20,f21,f23,f24,f25,f22,f11,f62,f128,f136,f115,f152");
        uriBuilder.addParameter("_", String.valueOf(timestamp));

        URI uri = uriBuilder.build();

        Map<String, Object> result = new HashMap<>();
        result.put("uri", uri);
        result.put("timestamp", timestamp);

        return result;
    }


    private void storeDataInMongo(String content, long timestamp) {
        // 对爬取的原始数据进行预处理
        int startIndex = content.indexOf('(') + 1;
        int endIndex = content.lastIndexOf(')');
        String jsonStringData = "[" + content.substring(startIndex, endIndex) + "]";

        // 定义存储的键值对
        Map<String, String> keyMapping = new HashMap<>();
        keyMapping.put("f12", "id");
        keyMapping.put("f2", "price");
        keyMapping.put("f3", "exchange");
        keyMapping.put("f5", "turnover");
        keyMapping.put("f6", "volume");
        keyMapping.put("f7", "amplitude");
        keyMapping.put("f14", "name");
        keyMapping.put("f15", "highest");
        keyMapping.put("f16", "lowest");

        JsonArray array = gson.fromJson(jsonStringData, JsonArray.class);
        for (JsonElement element : array) {
            if (element.isJsonObject()) {
                logger.info("isJsonObject");

                // 获取时间戳和格式化时间
                Date date = new Date(timestamp);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
                sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
                String formattedDate = sdf.format(date);
                long beijingTime = Long.parseLong(formattedDate);

                // 遍历网页数据中的多个股票数据，将每支股票的数据分别储存在一个 document 中
                JsonObject jsonObject = element.getAsJsonObject();
                JsonObject dataObject = jsonObject.getAsJsonObject("data");
                JsonArray diffArray = dataObject.getAsJsonArray("diff");
                if (diffArray != null) {
                    logger.info("diffArray != null");
                    for (int i = 0; i < diffArray.size(); i++) {
                        JsonObject rawStockData = diffArray.get(i).getAsJsonObject();
                        Document document = new Document();

                        // 将所需的键值对根据映射关系添加到 document 中
                        for (Map.Entry<String, String> entry : keyMapping.entrySet()) {
                            String originalKey = entry.getKey();
                            String newKey = entry.getValue();
                            if (rawStockData.has(originalKey)) {
                                document.append(newKey, rawStockData.get(originalKey).getAsString());
                            }
                        }
                        // 添加额外的时间戳和格式化时间
                        document.append("timestamp", date);
                        document.append("beijingTime", beijingTime);

                        // 存入 mongodb 数据库
                        MongoDatabase database = mongoClient.getDatabase("stocks");
                        MongoCollection<Document> collection = database.getCollection("stock_data");
                        collection.insertOne(document);
                        logger.info("insert data into collection stock_data");
                    }
                }
            }
        }
    }
}