package io.github.bhhan.portfolio.dashboard.common;

import lombok.Getter;
import lombok.Setter;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.util.Arrays;

@ConfigurationProperties("bhhan")
@Getter
@Setter
@Validated
public class UserProperties {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
