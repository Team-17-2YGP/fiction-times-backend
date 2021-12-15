package com.fictiontimes.fictiontimesbackend.controller.reader;

import com.fictiontimes.fictiontimesbackend.model.Episode;
import com.fictiontimes.fictiontimesbackend.repository.GenreRepository;
import com.fictiontimes.fictiontimesbackend.repository.StoryRepository;
import com.fictiontimes.fictiontimesbackend.service.StoryService;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/reader/story/episode/read")
public class GetEpisodeContentServlet extends HttpServlet {
    StoryService storyService = new StoryService(new StoryRepository(), new GenreRepository());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int episodeId = Integer.parseInt(request.getParameter("id"));

        storyService.getEpisodeContentByEpisodeId(episodeId);
        response.setContentType("text/html");
        response.getWriter().write(storyService.getEpisodeContentByEpisodeId(episodeId));
    }
}
