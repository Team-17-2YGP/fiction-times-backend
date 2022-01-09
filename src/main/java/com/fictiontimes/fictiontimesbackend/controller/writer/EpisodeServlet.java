package com.fictiontimes.fictiontimesbackend.controller.writer;

import com.fictiontimes.fictiontimesbackend.model.Episode;
import com.fictiontimes.fictiontimesbackend.repository.GenreRepository;
import com.fictiontimes.fictiontimesbackend.repository.StoryRepository;
import com.fictiontimes.fictiontimesbackend.repository.UserRepository;
import com.fictiontimes.fictiontimesbackend.service.StoryService;
import com.fictiontimes.fictiontimesbackend.utils.AuthUtils;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import com.fictiontimes.fictiontimesbackend.utils.FileUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.util.Date;

@WebServlet("/writer/episode")
@MultipartConfig
public class EpisodeServlet extends HttpServlet {

    StoryService storyService = new StoryService(new StoryRepository(), new GenreRepository(), new UserRepository());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        int userId = AuthUtils.getUserId(AuthUtils.extractAuthToken(request));
        int episodeId = Integer.parseInt(request.getParameter("id"));
        Episode episode = storyService.getEpisodeById(episodeId, userId);
        response.getWriter().write(CommonUtils.getGson().toJson(episode));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int storyId = Integer.parseInt(request.getParameter("storyId"));
        int episodeNumber = Integer.parseInt(request.getParameter("episodeNumber"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        Part part = request.getPart("content");
        String content = FileUtils.saveEpub(part, storyId, episodeNumber);
        Episode episode = new Episode(storyId, episodeNumber, title, description, 0, new Date(), content);
        storyService.saveEpisode(episode, AuthUtils.getUserId(request.getHeader("Authorization")));
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        int userId = AuthUtils.getUserId(AuthUtils.extractAuthToken(request));
        int storyId = Integer.parseInt(request.getParameter("storyId"));
        int episodeId = Integer.parseInt(request.getParameter("episodeId"));
        int episodeNumber = Integer.parseInt(request.getParameter("episodeNumber"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        Part part = request.getPart("content");
        String content = null;
        if(part.getSize() != 0) {
            content = FileUtils.saveEpub(part, storyId, episodeNumber);
        }
        Episode episode = new Episode(episodeId, storyId, episodeNumber, title, description, 0, null, content);
        storyService.updateEpisode(episode, userId);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        int userId = AuthUtils.getUserId(AuthUtils.extractAuthToken(request));
        int storyId = Integer.parseInt(request.getParameter("storyId"));
        int episodeId = Integer.parseInt(request.getParameter("episodeId"));
        storyService.deleteEpisode(episodeId, storyId, userId);
    }
}
