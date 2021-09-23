package io.github.bhhan.portfolio.gateway.filters.global;

import io.github.bhhan.portfolio.gateway.service.AuthValidateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class BasicAuthFilter implements GlobalFilter, Ordered {
    private final AuthValidateService authValidateService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final ServerHttpRequest request = exchange.getRequest();
        final HttpHeaders headers = request.getHeaders();
        final List<String> authorization = headers.get(HttpHeaders.AUTHORIZATION);

        if(Objects.nonNull(authorization)){
            request.mutate()
                .header(HttpHeaders.AUTHORIZATION, String.format("%s %s", "bearer", authValidateService.getAccessToken(authorization.get(0).split(" ")[1])));
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
