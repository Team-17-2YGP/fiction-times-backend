package com.fictiontimes.fictiontimesbackend.auth;

import com.fictiontimes.fictiontimesbackend.repository.UserRepository;
import com.fictiontimes.fictiontimesbackend.service.UserService;
import com.fictiontimes.fictiontimesbackend.utils.AuthUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/user/notification/read")
public class readNotificationServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService(new UserRepository());
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        int userId = AuthUtils.getUserId(AuthUtils.extractAuthToken(request));
        String strNotificationId = request.getParameter("id");
        if (strNotificationId != null && !strNotificationId.equals("")) {
            userService.markReadNotification(Integer.parseInt(strNotificationId), userId);
        } else {
            userService.markReadAllNotifications(userId);
        }
    }
}
