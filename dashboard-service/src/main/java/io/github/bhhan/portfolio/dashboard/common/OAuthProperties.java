package io.github.bhhan.portfolio.dashboard.common;

import lombok.Getter;
import lombok.Setter;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@ConfigurationProperties("oauth2")
@Getter
@Setter
@Validated
public class OAuthProperties {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public String getBasicAuthToken(){
        return new String(Base64.encode(String.format("%s:%s", username, password).getBytes()));
    }
}