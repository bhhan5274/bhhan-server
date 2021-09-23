package io.github.bhhan.portfolio.dashboard.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@ConfigurationProperties("dashboard")
@Getter
@Setter
@Validated
public class RedirectProperties {
    @NotEmpty
    private String redirectUrl;

    @NotEmpty
    private String gatewayUrl;
}
