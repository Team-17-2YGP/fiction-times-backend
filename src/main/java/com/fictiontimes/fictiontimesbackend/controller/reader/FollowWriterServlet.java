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

@WebServlet(urlPatterns = {"/reader/follow", "/reader/unfollow"})
public class FollowWriterServlet extends HttpServlet {

    ReaderService readerService = new ReaderService(new UserRepository(), new ReaderRepository(),
            new WriterRepository());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, InvalidTokenException, TokenExpiredException, DatabaseOperationException {
        response.setContentType("application/json");
        int readerId = AuthUtils.getUserId(AuthUtils.extractAuthToken(request));
        String reqWriterId = request.getParameter("id");
        int writerId = Integer.parseInt(reqWriterId);

        // Unfollow writer
        if (request.getRequestURI().endsWith("unfollow")) {
            readerService.unFollowWriter(readerId, writerId);
        } else { // Follow writer
            readerService.followWriter(readerId, writerId);
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
