package io.github.bhhan.portfolio.dashboard.common;

import io.github.bhhan.portfolio.dashboard.domain.user.Role;
import io.github.bhhan.portfolio.dashboard.service.user.AccountService;
import io.github.bhhan.portfolio.dashboard.service.user.RoleException;
import io.github.bhhan.portfolio.dashboard.service.user.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

@Configuration
@EnableConfigurationProperties({UserProperties.class, OAuthProperties.class})
@RequiredArgsConstructor
public class UserConfiguration {
    private final AccountService accountService;
    private final RoleService roleService;
    private final UserProperties userProperties;

    @Bean
    public ApplicationRunner initUser(){
        return args -> {
            registerRoles();
            registerAccount();
        };
    }

    private void registerRoles(){
        try{
            roleService.getRole(Role.ADMIN);
        }catch(RoleException e){
            roleService.addRole(Role.ADMIN);
        }
    }

    private void registerAccount(){
        try{
            accountService.loadUserByUsername(userProperties.getUsername());
        }catch(UsernameNotFoundException e){
            accountService.addAccount(userProperties.getUsername(), userProperties.getPassword(), Collections.singletonList(Role.ADMIN));
        }
    }
}
