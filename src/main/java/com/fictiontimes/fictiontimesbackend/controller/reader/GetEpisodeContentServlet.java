package com.fictiontimes.fictiontimesbackend.controller.reader;

import com.fictiontimes.fictiontimesbackend.repository.GenreRepository;
import com.fictiontimes.fictiontimesbackend.repository.StoryRepository;
import com.fictiontimes.fictiontimesbackend.repository.UserRepository;
import com.fictiontimes.fictiontimesbackend.service.StoryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/reader/story/episode/read")
public class GetEpisodeContentServlet extends HttpServlet {
    StoryService storyService = new StoryService(new StoryRepository(), new GenreRepository(), new UserRepository());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int episodeId = Integer.parseInt(request.getParameter("id"));

        String content = storyService.getEpisodeContentByEpisodeId(episodeId);
        if (content == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"error\": \"Episode not found\"}");
            return;
        }
        response.setContentType("text/html");
        response.getWriter().write(content);
    }
}
