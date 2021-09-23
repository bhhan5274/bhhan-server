package io.github.bhhan.portfolio.dashboard.security;

import io.github.bhhan.portfolio.dashboard.service.user.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(RedirectProperties.class)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final DataSource dataSource;
    private final RedirectProperties redirectProperties;

    @Bean
    public PersistentTokenRepository tokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/login", "/project/board", "/skill/board", "/actuator/health").permitAll()
                .anyRequest().authenticated()
            .and()
                .exceptionHandling()
                .accessDeniedHandler(new CustomAccessDeniedHandler(redirectProperties))
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint(redirectProperties))
            .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(new CustomAuthenticationSuccessHandler(redirectProperties))
                .failureHandler(new CustomAuthenticationFailureHandler(redirectProperties))
                .permitAll()
            .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(new CustomLogoutSuccessHandler(redirectProperties))
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            .and()
                .rememberMe()
                .rememberMeParameter("remember-me")
                .userDetailsService(accountService)
                .tokenRepository(tokenRepository())
            .and()
                .httpBasic()
            .and()
                .csrf()
                .disable()
                .headers()
                .frameOptions()
                .disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .mvcMatchers("/fontawesome/**")
                .mvcMatchers("/img/**")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService)
                .passwordEncoder(passwordEncoder);
    }
}
