package io.github.bhhan.portfolio.career;

import io.eventuate.util.spring.swagger.EventuateSwaggerConfig;
import io.github.bhhan.portfolio.career.serivce.S3StorageConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = "prod")
@Import({S3StorageConfiguration.class})
public class ProdS3StorageConfiguration {
    private static final String PROD_SWAGGER_DEFAULT_PACKAGE_NONE = "";

    @Bean
    public static EventuateSwaggerConfig eventuateSwaggerConfig(){
        return () -> PROD_SWAGGER_DEFAULT_PACKAGE_NONE;
    }
}
