package com.fictiontimes.fictiontimesbackend.controller.reader;

import com.fictiontimes.fictiontimesbackend.repository.*;
import com.fictiontimes.fictiontimesbackend.service.ReaderService;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/reader/genre")
public class GetGenreServlet extends HttpServlet {

    ReaderService readerService;

    @Override
    public void init() throws ServletException {
        readerService = new ReaderService(
                new UserRepository(), new ReaderRepository(),
                new WriterRepository(), new StoryRepository(),
                new GenreRepository()
        );
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().write(CommonUtils.getGson().toJson(readerService.getGenreList()));
    }
}
