package com.fictiontimes.fictiontimesbackend.auth;

import com.fictiontimes.fictiontimesbackend.model.User;
import com.fictiontimes.fictiontimesbackend.repository.UserRepository;
import com.fictiontimes.fictiontimesbackend.service.UserService;
import com.fictiontimes.fictiontimesbackend.utils.AuthUtils;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/user/password/reset")
public class PasswordResetServlet extends HttpServlet {

    UserService userService;

    @Override
    public void init() {
        userService = new UserService(new UserRepository());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = CommonUtils.getGson().fromJson(request.getReader(), User.class);
        userService.requestPasswordReset(user.getEmail());
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = CommonUtils.getGson().fromJson(request.getReader(), User.class);
        user.setUserId(AuthUtils.getUserId(request.getHeader("Authorization")));
        userService.resetPassword(user);
    }
}
