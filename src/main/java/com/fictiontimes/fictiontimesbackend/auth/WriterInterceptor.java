package com.fictiontimes.fictiontimesbackend.auth;

import com.fictiontimes.fictiontimesbackend.exception.TokenExpiredException;
import com.fictiontimes.fictiontimesbackend.exception.TokenNotFoundException;
import com.fictiontimes.fictiontimesbackend.model.Types.UserType;
import com.fictiontimes.fictiontimesbackend.utils.AuthUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class WriterInterceptor implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = null;
        try {
            token = AuthUtils.extractAuthToken(request);
        } catch (TokenNotFoundException e) {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            servletResponse.getWriter().write("{\"error\": \"Token not found, please login to continue\"}");
        }
        boolean isNotAuthorised = false;
        if (token != null) {
            try {
                if (AuthUtils.isAuthorised(token, UserType.WRITER)) {
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {
                    isNotAuthorised = true;
                }
            } catch (TokenExpiredException e) {
                HttpServletResponse response = (HttpServletResponse) servletResponse;
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                servletResponse.getWriter().write("{\"error\": \"Token expired, please login to continue\"}");
            }
        } else {
            isNotAuthorised = true;
        }
        if (isNotAuthorised) {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            servletResponse.getWriter().write("{\"error\": \"Unauthorised, YOU SHALL NOT PASS\"}");
        }
    }
}
