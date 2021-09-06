package com.fictiontimes.fictiontimesbackend.Auth;

import com.fictiontimes.fictiontimesbackend.Model.DTO.UserRegistrationErrorDTO;
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
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@WebServlet("/user/registration")
public class UserRegistrationServlet extends HttpServlet {

    private final UserService userService = new UserService(new UserRepository());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = CommonUtils.getGson().fromJson(request.getReader(), User.class);
        String payload;
        try {
            user = userService.createNewUser(user);
            payload = CommonUtils.getGson().toJson(user);
            response.addCookie(AuthUtils.generateAuthCookie(user));
        } catch (NoSuchAlgorithmException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            String message = e.getMessage();
            String error;
            if (message.endsWith("userName_uindex'")) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                error = "User name already exists";
            } else if (message.endsWith("user_email_uindex'")) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                error = "Account with the same email already exists";
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                error = message;
            }
            UserRegistrationErrorDTO errorDTO = new UserRegistrationErrorDTO(error, user);
            payload = CommonUtils.getGson().toJson(errorDTO);
        }
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.getWriter().write(payload);
    }
}
