package com.fictiontimes.fictiontimesbackend.controller.reader;

import com.fictiontimes.fictiontimesbackend.repository.*;
import com.fictiontimes.fictiontimesbackend.service.ReaderService;
import com.fictiontimes.fictiontimesbackend.utils.AuthUtils;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/reader/genre/like")
public class LikeGenreServlet extends HttpServlet {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = AuthUtils.getUserId(request.getHeader("Authorization"));
        List<Integer> genreIdList = CommonUtils.getGson().fromJson(
                request.getReader(), new TypeToken<List<Integer>>(){}.getType()
        );
        readerService.likeGenre(userId, genreIdList);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = AuthUtils.getUserId(request.getHeader("Authorization"));
        int genreId = Integer.parseInt(request.getParameter("id"));
        response.getWriter().write(CommonUtils.getGson().toJson(readerService.getGenreDetails(userId, genreId)));
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = AuthUtils.getUserId(request.getHeader("Authorization"));
        int genreId = Integer.parseInt(request.getParameter("id"));
        readerService.unlikeGenre(userId, genreId);
    }
}
