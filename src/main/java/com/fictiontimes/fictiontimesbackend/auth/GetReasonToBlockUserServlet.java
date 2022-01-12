package com.fictiontimes.fictiontimesbackend.auth;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.exception.InvalidTokenException;
import com.fictiontimes.fictiontimesbackend.exception.TokenExpiredException;
import com.fictiontimes.fictiontimesbackend.model.Auth.TokenBody;
import com.fictiontimes.fictiontimesbackend.repository.UserRepository;
import com.fictiontimes.fictiontimesbackend.service.UserService;
import com.fictiontimes.fictiontimesbackend.utils.AuthUtils;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/user/block/reason")
public class GetReasonToBlockUserServlet extends HttpServlet {
    UserService userService = new UserService(new UserRepository());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, InvalidTokenException, TokenExpiredException, DatabaseOperationException {
        int userId = AuthUtils.getUserId(AuthUtils.extractAuthToken(request));
        userService.getReasonToBlock(userId);
        response.getWriter().write(CommonUtils.getGson().toJson(userService.getReasonToBlock(userId)));
    }
}
