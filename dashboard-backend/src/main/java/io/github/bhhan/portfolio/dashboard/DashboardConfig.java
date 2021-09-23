package io.github.bhhan.portfolio.dashboard;

import io.github.bhhan.portfolio.dashboard.domain.user.AccountRepository;
import io.github.bhhan.portfolio.dashboard.domain.user.RoleRepository;
import io.github.bhhan.portfolio.dashboard.service.user.AccountService;
import io.github.bhhan.portfolio.dashboard.service.user.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableJpaRepositories
@RequiredArgsConstructor
@EnableAutoConfiguration
public class DashboardConfig {
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public AccountService accountService(RoleService roleService){
        return new AccountService(accountRepository, passwordEncoder, roleService);
    }

    @Bean
    public RoleService roleService(){
        return new RoleService(roleRepository);
    }
}
