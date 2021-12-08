package com.fictiontimes.fictiontimesbackend.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.IOException;
import java.io.PrintWriter;

public class ResponseInterceptor implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final CopyPrintWriter writer = new CopyPrintWriter(servletResponse.getWriter());
        servletResponse.setContentType("application/json");
        filterChain.doFilter(servletRequest, new HttpServletResponseWrapper((HttpServletResponse) servletResponse) {
            @Override
            public PrintWriter getWriter() {
                return writer;
            }
        });

        String responseBody = writer.getCopy();
        if (responseBody == null || responseBody.equals("")) {
            servletResponse.getWriter().write("{\"status\": \"Successful\"}");
        }
    }
}
