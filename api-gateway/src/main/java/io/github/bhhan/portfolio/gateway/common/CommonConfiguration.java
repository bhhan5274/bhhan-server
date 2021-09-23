package io.github.bhhan.portfolio.gateway.common;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@EnableConfigurationProperties({OAuth2Properties.class, ProxyProperties.class})
public class CommonConfiguration {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public WebClient webClient(){
        return WebClient.create();
    }
}
