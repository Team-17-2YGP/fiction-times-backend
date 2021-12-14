package com.fictiontimes.fictiontimesbackend.controller.reader;

import com.fictiontimes.fictiontimesbackend.repository.ReaderRepository;
import com.fictiontimes.fictiontimesbackend.repository.StoryRepository;
import com.fictiontimes.fictiontimesbackend.repository.UserRepository;
import com.fictiontimes.fictiontimesbackend.repository.WriterRepository;
import com.fictiontimes.fictiontimesbackend.service.ReaderService;
import com.fictiontimes.fictiontimesbackend.utils.AuthUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = {"/reader/story/like", "/reader/story/unlike"})
public class LikeStoryServlet extends HttpServlet {

    ReaderService readerService = new ReaderService(new UserRepository(), new ReaderRepository(),
            new WriterRepository(), new StoryRepository());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int readerId = AuthUtils.getUserId(AuthUtils.extractAuthToken(request));
        int storyId = Integer.parseInt(request.getParameter("id"));
        readerService.likeUnlikeStory(readerId, storyId, !request.getRequestURI().endsWith("unlike"));
    }
}
