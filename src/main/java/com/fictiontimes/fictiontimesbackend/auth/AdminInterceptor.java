package com.fictiontimes.fictiontimesbackend.auth;

import com.fictiontimes.fictiontimesbackend.model.Types.UserType;
import com.fictiontimes.fictiontimesbackend.utils.AuthUtils;
import com.fictiontimes.fictiontimesbackend.exception.CookieExpiredException;
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
            try {
                if (AuthUtils.isAuthorised(token, UserType.ADMIN)) {
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {
                    isNotAuthorised = true;
                }
            } catch (CookieExpiredException e) {
                HttpServletResponse response = (HttpServletResponse) servletResponse;
                response.addCookie(AuthUtils.removeAuthCookie());
                e.printStackTrace();
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
