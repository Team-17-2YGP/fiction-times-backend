package com.example.fictionTimesBackend.Auth;

import com.example.fictionTimesBackend.Model.User;
import com.example.fictionTimesBackend.Repository.UserRepository;
import com.example.fictionTimesBackend.Service.UserService;
import com.example.fictionTimesBackend.Utils.AuthUtils;
import com.example.fictionTimesBackend.Utils.CommonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/user/login")
public class LogInServlet extends HttpServlet {

    private final UserService userService = new UserService(new UserRepository());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = CommonUtils.getGson().fromJson(request.getReader(), User.class);
        try {
            User matchedUser = userService.checkCredentials(user);
            if (matchedUser != null) {
                response.setStatus(200);
                response.addCookie(AuthUtils.generateAuthCookie(matchedUser));
            } else {
                response.setStatus(401);
            }
        } catch (SQLException | ClassNotFoundException exception) {
            response.setStatus(500);
            exception.printStackTrace();
        }
    }
}
