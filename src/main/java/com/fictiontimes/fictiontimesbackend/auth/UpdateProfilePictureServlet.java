package com.fictiontimes.fictiontimesbackend.auth;

import com.fictiontimes.fictiontimesbackend.repository.UserRepository;
import com.fictiontimes.fictiontimesbackend.service.UserService;
import com.fictiontimes.fictiontimesbackend.utils.AuthUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;

@WebServlet("/user/update/profile/picture")
@MultipartConfig
public class UpdateProfilePictureServlet extends HttpServlet {
    UserService userService = new UserService(new UserRepository());

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        int userId = AuthUtils.getUserId(AuthUtils.extractAuthToken(request));
        Part profilePicture = request.getPart("profilePicture");
        userService.updateProfilePicture(userId, profilePicture);
    }
}
