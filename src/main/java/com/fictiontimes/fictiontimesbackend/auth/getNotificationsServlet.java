package com.fictiontimes.fictiontimesbackend.auth;

import com.fictiontimes.fictiontimesbackend.model.Notification;
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
import java.util.List;

@WebServlet("/user/notifications")
public class getNotificationsServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService(new UserRepository());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = AuthUtils.getUserId(AuthUtils.extractAuthToken(request));
        List<Notification> notifications = userService.getUnreadNotifications(userId);
        response.getWriter().write(CommonUtils.getGson().toJson(notifications));
    }
}
