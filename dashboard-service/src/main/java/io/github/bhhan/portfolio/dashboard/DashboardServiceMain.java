package io.github.bhhan.portfolio.dashboard;

import io.github.bhhan.portfolio.dashboard.common.CommonConfig;
import io.github.bhhan.portfolio.dashboard.common.UserConfiguration;
import io.github.bhhan.portfolio.dashboard.security.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({DashboardConfig.class, CommonConfig.class, SecurityConfig.class, UserConfiguration.class})
public class DashboardServiceMain {
    public static void main(String[] args) {
        SpringApplication.run(DashboardServiceMain.class, args);
    }
}
