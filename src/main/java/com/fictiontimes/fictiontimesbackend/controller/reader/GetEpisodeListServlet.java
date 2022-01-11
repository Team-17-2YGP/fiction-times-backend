package com.fictiontimes.fictiontimesbackend.controller.reader;

import com.fictiontimes.fictiontimesbackend.model.DTO.ReaderEpisodeDTO;
import com.fictiontimes.fictiontimesbackend.repository.*;
import com.fictiontimes.fictiontimesbackend.service.ReaderService;
import com.fictiontimes.fictiontimesbackend.utils.AuthUtils;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/reader/story/episodes")
public class GetEpisodeListServlet extends HttpServlet {

    ReaderService readerService = new ReaderService(
            new UserRepository(), new ReaderRepository(), new WriterRepository(), new StoryRepository(),
            new GenreRepository()
    );

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int storyId = Integer.parseInt(request.getParameter("id"));
        int readerId = AuthUtils.getUserId(request.getHeader("Authorization"));
        List<ReaderEpisodeDTO> episodeList = readerService.getEpisodeListByStory(storyId, readerId);
        response.getWriter().write(CommonUtils.getGson().toJson(episodeList));
    }
}
