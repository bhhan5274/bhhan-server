package io.github.bhhan.portfolio.oauth2;

import io.github.bhhan.portfolio.oauth2.domain.AccountRepository;
import io.github.bhhan.portfolio.oauth2.domain.RoleRepository;
import io.github.bhhan.portfolio.oauth2.mapper.ClientDetailsMapper;
import io.github.bhhan.portfolio.oauth2.mapper.RoleMapper;
import io.github.bhhan.portfolio.oauth2.service.AccountService;
import io.github.bhhan.portfolio.oauth2.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BackendConfiguration {
    private final PasswordEncoder passwordEncoder;

    @Bean
    public ClientDetailsMapper clientDetailsMapper(){
        return new ClientDetailsMapper();
    }

    @Bean
    public RoleMapper roleMapper(){
        return new RoleMapper();
    }

    @Bean
    public RoleService roleService(RoleRepository roleRepository, RoleMapper roleMapper){
        return new RoleService(roleRepository, roleMapper);
    }

    @Bean
    public AccountService accountService(AccountRepository accountRepository, RoleRepository roleRepository, ClientDetailsMapper clientDetailsMapper){
        return new AccountService(accountRepository, roleRepository, passwordEncoder, clientDetailsMapper);
    }
}
