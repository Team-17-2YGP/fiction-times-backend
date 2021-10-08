package com.fictiontimes.fictiontimesbackend.auth;

import com.fictiontimes.fictiontimesbackend.model.User;
import com.fictiontimes.fictiontimesbackend.repository.UserRepository;
import com.fictiontimes.fictiontimesbackend.service.UserService;
import com.fictiontimes.fictiontimesbackend.utils.AuthUtils;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
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
                response.getWriter().print(AuthUtils.generateAuthToken(matchedUser));
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException | ClassNotFoundException exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            exception.printStackTrace();
        }
    }
}
