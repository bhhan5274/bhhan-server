package io.github.bhhan.portfolio.career;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@ConfigurationProperties("storage")
@Getter
@Setter
@Validated
public class StorageProperties {
    private String location = "upload-dir";

    @NotEmpty
    private String gatewayUrl;
}
