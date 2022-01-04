package com.fictiontimes.fictiontimesbackend.controller.reader;

import com.fictiontimes.fictiontimesbackend.repository.ReaderRepository;
import com.fictiontimes.fictiontimesbackend.repository.StoryRepository;
import com.fictiontimes.fictiontimesbackend.repository.UserRepository;
import com.fictiontimes.fictiontimesbackend.repository.WriterRepository;
import com.fictiontimes.fictiontimesbackend.service.ReaderService;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/reader/story/genre")
public class GetStoryByGenreServlet extends HttpServlet {

    ReaderService readerService;

    @Override
    public void init() throws ServletException {
        readerService = new ReaderService(
                new UserRepository(), new ReaderRepository(),
                new WriterRepository(), new StoryRepository()
        );
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int genreId = Integer.parseInt(request.getParameter("id"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        response.getWriter().write(CommonUtils.getGson().toJson(readerService.getStoryListByGenre(genreId, limit)));
    }
}
