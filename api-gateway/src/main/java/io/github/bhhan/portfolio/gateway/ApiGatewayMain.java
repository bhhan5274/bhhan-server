package io.github.bhhan.portfolio.gateway;

import io.github.bhhan.portfolio.gateway.proxy.ProxyConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@Import({ProxyConfiguration.class})
public class ApiGatewayMain {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayMain.class, args);
    }
}