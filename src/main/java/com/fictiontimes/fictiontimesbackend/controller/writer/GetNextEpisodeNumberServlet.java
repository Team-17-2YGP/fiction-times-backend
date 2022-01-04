package com.fictiontimes.fictiontimesbackend.controller.writer;

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

@WebServlet("/writer/episode/number")
public class GetNextEpisodeNumberServlet extends HttpServlet {
    StoryService storyService = new StoryService(new StoryRepository(), new GenreRepository(), new UserRepository());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int storyId = Integer.parseInt(request.getParameter("id"));
        String resp = "{ \"nextEpisodeNumber\": " + storyService.getNextEpisodeNumberByStoryId(storyId) + " }";
        response.getWriter().write(resp);
    }
}
