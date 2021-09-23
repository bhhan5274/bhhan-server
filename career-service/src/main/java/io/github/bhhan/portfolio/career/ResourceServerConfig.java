package io.github.bhhan.portfolio.career;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableConfigurationProperties(OAuth2Properties.class)
@RequiredArgsConstructor
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    private final OAuth2Properties oAuth2Properties;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/files/**").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/**").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.PUT, "/v1/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.DELETE, "/v1/**").access("#oauth2.hasScope('write')")
                .anyRequest()
                .authenticated()
            .and()
                .csrf()
                .disable()
                .headers()
                .frameOptions()
                .disable();
    }

    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter(){
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(oAuth2Properties.getJwtSigningKey());
        return converter;
    }
}
