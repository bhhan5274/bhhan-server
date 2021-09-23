package io.github.bhhan.portfolio.career;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CareerWebConfiguration implements WebMvcConfigurer {

    @Bean
    public HttpMessageConverters messageConverters() {
        MappingJackson2HttpMessageConverter additional = new MappingJackson2HttpMessageConverter();
        return new HttpMessageConverters(additional);
    }
}
