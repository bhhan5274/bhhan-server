package io.github.bhhan.portfolio.oauth2;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "oauth2")
@Validated
@Getter
@Setter
public class OAuth2Properties {
    @NotEmpty
    private String jwtSigningKey;
}
