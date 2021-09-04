package com.example.fictionTimesBackend.Auth;

import com.example.fictionTimesBackend.Model.DTO.UserRegistrationErrorDTO;
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
                response.setStatus(400);
                error = "User name already exists";
            } else if (message.endsWith("user_email_uindex'")) {
                response.setStatus(400);
                error = "Account with the same email already exists";
            } else {
                response.setStatus(500);
                error = message;
            }
            UserRegistrationErrorDTO errorDTO = new UserRegistrationErrorDTO(error, user);
            payload = CommonUtils.getGson().toJson(errorDTO);
        }
        response.setContentType("application/json");
        response.getWriter().write(payload);
    }
}
