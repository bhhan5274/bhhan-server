package io.github.bhhan.portfolio.career;

import io.github.bhhan.portfolio.common.swagger.CommonSwaggerConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = "dev")
@Import({CommonSwaggerConfiguration.class, FileSystemStorageConfiguration.class})
public class SwaggerAndLocalStorageConfiguration {
}
