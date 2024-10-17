package st.backend.stocks.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfig {

    @Bean(value = "dockerBean")
    public Docket dockerBean() {
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .description("# 股票基本数据 RESTful APIs")
                        .version("1.0")
                        .build())
                .groupName("股票基本数据")
                .select()
                .apis(RequestHandlerSelectors.basePackage("st.backend.stocks.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

}
