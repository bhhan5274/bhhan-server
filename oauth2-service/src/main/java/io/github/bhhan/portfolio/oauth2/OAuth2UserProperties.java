package io.github.bhhan.portfolio.oauth2;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;
import java.util.List;

@ConfigurationProperties(prefix = "user")
@Validated
@Getter
@Setter
public class OAuth2UserProperties {
    @NotEmpty
    private String username;

    @Email(message = "잘못된 Email 형식입니다.")
    private String email;

    @Pattern(regexp = "^[!@#$%^&*A-Za-z0-9_-]{8,20}$", message = "잘못된 Password 형식입니다.")
    private String password;

    @NotEmpty
    private String clientId;

    @NotEmpty
    private String clientSecret;

    @NotNull(message = "항상 GrantType 포함해야 합니다.")
    @Size(min = 1, message = "GrantType 1개 이상 입니다.")
    private List<String> grantTypes;

    @NotNull(message = "항상 Scope 포함해야 합니다.")
    @Size(min = 1, message = "Scope 1개 이상 입니다.")
    private List<String> scopes;
}
