package io.github.bhhan.portfolio.career;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableResourceServer
@SpringBootApplication
@EnableJpaAuditing
@Import({CareerConfig.class, CareerWebConfiguration.class, ProdS3StorageConfiguration.class, ResourceServerConfig.class})
public class CareerServiceMain {
    public static void main(String[] args) {
        SpringApplication.run(CareerServiceMain.class, args);
    }
}
