package io.github.bhhan.portfolio.gateway.proxy;

import io.github.bhhan.portfolio.gateway.common.CommonConfiguration;
import io.github.bhhan.portfolio.gateway.common.ProxyProperties;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.time.Duration;

@Configuration
@Import({CommonConfiguration.class})
@RequiredArgsConstructor
public class ProxyConfiguration {
    private final ProxyProperties proxyProperties;

    @Bean
    public TimeLimiterRegistry timeLimiterRegistry(){
        return TimeLimiterRegistry.of(TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofMillis(proxyProperties.getTimeoutMillis()))
                .build());
    }

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer(){
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
            .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
            .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofMillis(proxyProperties.getTimeoutMillis())).build())
                .build());
    }
}
