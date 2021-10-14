package com.fictiontimes.fictiontimesbackend.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CORSInterceptor implements Filter {

    private static final String[] allowedOrigins = {
            "http://localhost:3000", "http://localhost:5500", "http://localhost:5501",
            "http://127.0.0.1:3000", "http://127.0.0.1:5500", "http://127.0.0.1:5501",
            "http://46.101.159.4", "https://46.101.159.4", "http://46.101.159.4:8181", 
            "http://fictiontimes.live", "http://fictiontimes.live:81", "http://fictiontimes.live:82",
            "http://fictiontimes.live:83",  "http://fictiontimes.live:8181"
    };

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String requestOrigin = request.getHeader("Origin");
        if(isAllowedOrigin(requestOrigin)) {
            // Authorize the origin, all headers, and all methods
            ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Origin", requestOrigin);
            ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Headers", "*");
            ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Methods",
                    "GET, OPTIONS, HEAD, PUT, POST, DELETE");

            HttpServletResponse resp = (HttpServletResponse) servletResponse;

            // CORS handshake (pre-flight request)
            if (request.getMethod().equals("OPTIONS")) {
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                return;
            }
        }
        // pass the request along the filter chain
        filterChain.doFilter(request, servletResponse);
    }

    private boolean isAllowedOrigin(String origin){
        if (origin == null) return false;
        for (String allowedOrigin : allowedOrigins) {
            if(origin.equals(allowedOrigin)) return true;
        }
        return false;
    }
}
