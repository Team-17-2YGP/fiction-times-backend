package com.fictiontimes.fictiontimesbackend.controller.admin;


import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.model.DTO.ErrorDTO;
import com.fictiontimes.fictiontimesbackend.model.Genre;
import com.fictiontimes.fictiontimesbackend.repository.GenreRepository;
import com.fictiontimes.fictiontimesbackend.repository.StoryRepository;
import com.fictiontimes.fictiontimesbackend.service.StoryService;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/genre")
public class GenreServlet extends HttpServlet {

    private final StoryService storyService = new StoryService(new StoryRepository(), new GenreRepository());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Genre genre = CommonUtils.getGson().fromJson(request.getReader(), Genre.class);
        String payload;
        try {
            genre = storyService.createNewGenre(genre);
            payload = CommonUtils.getGson().toJson(genre);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (DatabaseOperationException e) {
            e.printStackTrace();
            String message = e.getMessage();
            String error;
            if (message.endsWith("genreName_uindex'")) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                error = "Genre already exists";
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                error = message;
            }
            ErrorDTO<Genre> errorDTO = new ErrorDTO<>(error, genre);
            payload = CommonUtils.getGson().toJson(errorDTO);
        }
        response.getWriter().write(payload);
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, DatabaseOperationException {
        String[] parameterValues = request.getParameterValues("id");
        if (parameterValues != null) {
            storyService.deleteGenreById(Integer.parseInt(parameterValues[0]));
            response.setStatus((HttpServletResponse.SC_OK));
        }
    }
}
