package io.github.bhhan.portfolio.dashboard.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private final RedirectProperties redirectProperties;

    public CustomAuthenticationEntryPoint(RedirectProperties redirectProperties) {
        this.redirectProperties = redirectProperties;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        redirectStrategy.sendRedirect(request, response, redirectProperties.getRedirectUrl());
    }
}
