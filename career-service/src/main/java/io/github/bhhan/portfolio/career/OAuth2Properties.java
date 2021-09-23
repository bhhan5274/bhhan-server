package io.github.bhhan.portfolio.career;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@ConfigurationProperties(prefix = "oauth2")
@Validated
@Getter
@Setter
public class OAuth2Properties {
    @NotEmpty
    private String jwtSigningKey;
}
