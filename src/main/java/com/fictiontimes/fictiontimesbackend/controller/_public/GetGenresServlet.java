package com.fictiontimes.fictiontimesbackend.controller._public;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.repository.GenreRepository;
import com.fictiontimes.fictiontimesbackend.repository.StoryRepository;
import com.fictiontimes.fictiontimesbackend.repository.UserRepository;
import com.fictiontimes.fictiontimesbackend.service.StoryService;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/public/genres")
public class GetGenresServlet extends HttpServlet {
    private final StoryService storyService = new StoryService(new StoryRepository(), new GenreRepository(), new UserRepository());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, DatabaseOperationException {
        response.getWriter().write(CommonUtils.getGson().toJson(storyService.getGenreList()));
    }
}
