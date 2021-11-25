package com.fictiontimes.fictiontimesbackend.controller;

import jakarta.servlet.*;

import java.io.IOException;

public class ResponseInterceptor implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);

        String contentType = servletResponse.getContentType();
        if (contentType == null || contentType.equals("")) {
            servletResponse.setContentType("application/json");
        }
    }
}
