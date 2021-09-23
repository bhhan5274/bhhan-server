package io.github.bhhan.portfolio.common.swagger;

import io.eventuate.util.spring.swagger.EventuateSwaggerConfig;
import org.springframework.context.annotation.Bean;

public class CommonSwaggerConfiguration {
    private static final String DEFAULT_PACKAGE_NAME = "io.github.bhhan.portfolio";

    @Bean
    public static EventuateSwaggerConfig eventuateSwaggerConfig(){
        return () -> DEFAULT_PACKAGE_NAME;
    }
}
