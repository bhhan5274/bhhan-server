package io.github.bhhan.portfolio.gateway.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@ConfigurationProperties(prefix = "proxy")
@Validated
@Getter
@Setter
public class ProxyProperties {
    @NotNull
    private Long timeoutMillis;
}
