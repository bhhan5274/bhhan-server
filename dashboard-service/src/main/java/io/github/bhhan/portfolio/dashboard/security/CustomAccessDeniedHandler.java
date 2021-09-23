package io.github.bhhan.portfolio.dashboard.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private final RedirectProperties redirectProperties;

    public CustomAccessDeniedHandler(RedirectProperties redirectProperties) {
        this.redirectProperties = redirectProperties;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        redirectStrategy.sendRedirect(request, response, redirectProperties.getRedirectUrl());
    }
}
