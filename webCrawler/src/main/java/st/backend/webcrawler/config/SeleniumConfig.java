package st.backend.webcrawler.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeleniumConfig {

    @Value("${chrome.driver.local.path}")
    private String localChromeDriverPath;

    @Value("${chrome.driver.docker.path}")
    private String dockerChromeDriverPath;

    public String getLocalChromeDriverPath() {
        return localChromeDriverPath;
    }

    public String getDockerChromeDriverPath() {
        return dockerChromeDriverPath;
    }
}
