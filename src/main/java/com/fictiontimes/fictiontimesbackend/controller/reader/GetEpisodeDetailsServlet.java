package com.fictiontimes.fictiontimesbackend.controller.reader;

import com.fictiontimes.fictiontimesbackend.model.DTO.ReaderEpisodeDTO;
import com.fictiontimes.fictiontimesbackend.repository.GenreRepository;
import com.fictiontimes.fictiontimesbackend.repository.StoryRepository;
import com.fictiontimes.fictiontimesbackend.repository.UserRepository;
import com.fictiontimes.fictiontimesbackend.service.StoryService;
import com.fictiontimes.fictiontimesbackend.utils.AuthUtils;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/reader/story/episode/details")
public class GetEpisodeDetailsServlet extends HttpServlet {

    StoryService storyService = new StoryService(new StoryRepository(), new GenreRepository(), new UserRepository());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int readerId = AuthUtils.getUserId(AuthUtils.extractAuthToken(request));
        int episodeId = Integer.parseInt(request.getParameter("id"));
        ReaderEpisodeDTO episode = storyService.getEpisodeDetails(readerId, episodeId);
        if (episode == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"error\": \"Episode not found\"}");
            return;
        }
        response.getWriter().write(CommonUtils.getGson().toJson(episode));
    }
}
