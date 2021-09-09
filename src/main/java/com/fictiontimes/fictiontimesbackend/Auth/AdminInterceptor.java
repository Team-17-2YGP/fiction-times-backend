package com.fictiontimes.fictiontimesbackend.Auth;

import com.fictiontimes.fictiontimesbackend.Model.Types.UserType;
import com.fictiontimes.fictiontimesbackend.Utils.AuthUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/admin/*")
public class AdminInterceptor implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = AuthUtils.extractAuthToken(request.getCookies());
        boolean isNotAuthorised = false;
        if (token != null) {
            if (AuthUtils.isAuthorised(token, UserType.ADMIN)) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                isNotAuthorised = true;
            }
        } else {
            isNotAuthorised = true;
        }
        if (isNotAuthorised) {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            servletResponse.getWriter().write("\"error\": \"Unauthorised, YOU SHALL NOT PASS\"");
        }
    }
}
