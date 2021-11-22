package com.fictiontimes.fictiontimesbackend.controller.reader;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.exception.InvalidTokenException;
import com.fictiontimes.fictiontimesbackend.exception.TokenExpiredException;
import com.fictiontimes.fictiontimesbackend.repository.ReaderRepository;
import com.fictiontimes.fictiontimesbackend.repository.UserRepository;
import com.fictiontimes.fictiontimesbackend.repository.WriterRepository;
import com.fictiontimes.fictiontimesbackend.service.ReaderService;
import com.fictiontimes.fictiontimesbackend.utils.AuthUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = {"/reader/subscribeNotifications", "/reader/unsubscribeNotifications"})
public class SubscribeNotificationsServlet extends HttpServlet {

    ReaderService readerService = new ReaderService(new UserRepository(), new ReaderRepository(), new WriterRepository());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, InvalidTokenException, TokenExpiredException, DatabaseOperationException {
        response.setContentType("application/json");
        int readerId = AuthUtils.getUserId(AuthUtils.extractAuthToken(request));
        String reqWriterId = request.getParameter("id");
        int writerId = Integer.parseInt(reqWriterId);
        boolean subscribe = !request.getRequestURI().endsWith("unsubscribeNotifications");
        readerService.setNotificationStatus(readerId, writerId, subscribe);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
