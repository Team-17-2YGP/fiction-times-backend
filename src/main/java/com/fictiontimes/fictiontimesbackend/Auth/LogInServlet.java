package com.fictiontimes.fictiontimesbackend.Auth;

import com.fictiontimes.fictiontimesbackend.Model.User;
import com.fictiontimes.fictiontimesbackend.Repository.UserRepository;
import com.fictiontimes.fictiontimesbackend.Service.UserService;
import com.fictiontimes.fictiontimesbackend.Utils.AuthUtils;
import com.fictiontimes.fictiontimesbackend.Utils.CommonUtils;
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
                response.setStatus(HttpServletResponse.SC_ACCEPTED);
                response.addCookie(AuthUtils.generateAuthCookie(matchedUser));
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException | ClassNotFoundException exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            exception.printStackTrace();
        }
    }
}
