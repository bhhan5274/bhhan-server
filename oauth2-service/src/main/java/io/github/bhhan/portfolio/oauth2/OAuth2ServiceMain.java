package io.github.bhhan.portfolio.oauth2;

import io.github.bhhan.portfolio.oauth2.domain.Account;
import io.github.bhhan.portfolio.oauth2.domain.Role;
import io.github.bhhan.portfolio.oauth2.service.AccountService;
import io.github.bhhan.portfolio.oauth2.service.RoleService;
import io.github.bhhan.portfolio.oauth2.web.api.AccountDto;
import io.github.bhhan.portfolio.oauth2.web.api.ClientDetailsDto;
import io.github.bhhan.portfolio.oauth2.web.api.RoleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.Arrays;
import java.util.HashSet;

@Slf4j
@SpringBootApplication
@Import({UtilsConfig.class, OAuth2Configuration.class, SecurityConfig.class})
@EnableConfigurationProperties({OAuth2UserProperties.class})
@RequiredArgsConstructor
public class OAuth2ServiceMain {
    private final AccountService accountService;
    private final RoleService roleService;
    private final OAuth2UserProperties oAuth2UserProperties;

    public static void main(String[] args) {
        SpringApplication.run(OAuth2ServiceMain.class, args);
    }

    @Bean
    public CommandLineRunner initOAuth2User(){
        return args -> {
            try{
                final Role admin = roleService.addRole(RoleDto.RoleReq.builder().name("ADMIN").build());
                final Role client = roleService.addRole(RoleDto.RoleReq.builder().name("CLIENT").build());

                final Account account = accountService.addAccount(AccountDto.AccountReq.builder()
                        .name(oAuth2UserProperties.getUsername())
                        .email(oAuth2UserProperties.getEmail())
                        .password(oAuth2UserProperties.getPassword())
                        .roleIds(Arrays.asList(admin.getId(), client.getId()))
                        .build());

                final ClientDetailsDto clientDetailsDto = ClientDetailsDto.builder()
                        .clientId(oAuth2UserProperties.getClientId())
                        .clientSecret(oAuth2UserProperties.getClientSecret())
                        .authorizedGrantTypes(new HashSet<>(oAuth2UserProperties.getGrantTypes()))
                        .scope(new HashSet<>(oAuth2UserProperties.getScopes()))
                        .build();

                accountService.addClientDetailsWithClientIdAndClientSecret(account.getId(), clientDetailsDto);
            }catch (Exception e){
                log.info("USER, ROLE이 이미 추가되어 있습니다.");
            }
        };
    }
}