package io.github.bhhan.portfolio.dashboard.service;

import io.github.bhhan.portfolio.dashboard.DashboardConfig;
import io.github.bhhan.portfolio.dashboard.domain.user.AccountRepository;
import io.github.bhhan.portfolio.dashboard.domain.user.RoleRepository;
import io.github.bhhan.portfolio.dashboard.service.user.AccountService;
import io.github.bhhan.portfolio.dashboard.service.user.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

abstract public class BaseServiceTest {

    @Configuration
    @Import({DashboardConfig.class})
    public static class Config {
        @Bean
        public PasswordEncoder passwordEncoder(){
            return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }
    }

    @Autowired
    protected RoleService roleService;

    @Autowired
    protected AccountService accountService;

    @Autowired
    protected AccountRepository accountRepository;

    @Autowired
    protected RoleRepository roleRepository;
}
