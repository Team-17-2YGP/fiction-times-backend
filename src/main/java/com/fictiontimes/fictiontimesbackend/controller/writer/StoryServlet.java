package com.fictiontimes.fictiontimesbackend.controller.writer;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.exception.InvalidTokenException;
import com.fictiontimes.fictiontimesbackend.exception.TokenExpiredException;
import com.fictiontimes.fictiontimesbackend.exception.TokenNotFoundException;
import com.fictiontimes.fictiontimesbackend.model.DTO.ErrorDTO;
import com.fictiontimes.fictiontimesbackend.model.Genre;
import com.fictiontimes.fictiontimesbackend.model.Story;
import com.fictiontimes.fictiontimesbackend.model.Types.StoryStatus;
import com.fictiontimes.fictiontimesbackend.repository.GenreRepository;
import com.fictiontimes.fictiontimesbackend.repository.StoryRepository;
import com.fictiontimes.fictiontimesbackend.repository.UserRepository;
import com.fictiontimes.fictiontimesbackend.service.StoryService;
import com.fictiontimes.fictiontimesbackend.utils.AuthUtils;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/writer/story")
@MultipartConfig
public class StoryServlet extends HttpServlet {

    private final StoryService storyService = new StoryService(new StoryRepository(), new GenreRepository(), new UserRepository());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, InvalidTokenException, TokenExpiredException, DatabaseOperationException {
        int userId = AuthUtils.getUserId(AuthUtils.extractAuthToken(request));
        String requestStoryId = request.getParameter("id");
        if (requestStoryId != null && !requestStoryId.equals("")) {
            int storyId = Integer.parseInt(requestStoryId);
            Story story = storyService.getStoryById(storyId, userId);
            if (story == null) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("{\"error\": \"Invalid storyId or user is forbidden to access " +
                        "resource\" }");
                return;
            }
            response.getWriter().write(CommonUtils.getGson().toJson(story));
        } else { // return all the stories created by the user
            List<Story> storyList = storyService.getStoryList(userId);
            response.getWriter().write(CommonUtils.getGson().toJson(storyList));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            IOException, TokenNotFoundException, InvalidTokenException, TokenExpiredException, ServletException {
        String payload;
        Story story = null;
        Gson gson = CommonUtils.getGson();
        try {
            int userId = AuthUtils.getUserId(AuthUtils.extractAuthToken(request));
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            StoryStatus status = StoryStatus.valueOf(request.getParameter("status"));
            // Set current time as the releasedDate
            Timestamp releasedDate = new Timestamp(new Date().getTime());
            Part coverArt = request.getPart("coverArt");

            Type genreListType = new TypeToken<ArrayList<Genre>>() {
            }.getType();
            List<Genre> genreArray = gson.fromJson(request.getParameter("genres"), genreListType);

            Type tagListType = new TypeToken<ArrayList<String>>() {
            }.getType();
            List<String> tagArray = gson.fromJson(request.getParameter("tags"), tagListType);

            story = new Story(0, userId, title, description, 0, "", coverArt,
                    status, releasedDate, tagArray, genreArray);

            story = storyService.createNewStory(story);

            payload = CommonUtils.getGson().toJson(story);
            response.setStatus(HttpServletResponse.SC_CREATED);

        } catch (DatabaseOperationException e) {
            e.printStackTrace();
            String message = e.getMessage();
            String error;
            if (message.endsWith("title_uindex'")) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                error = "Story title already exists";
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                error = message;
            }
            ErrorDTO<Story> errorDTO = new ErrorDTO<>(error, story);
            payload = CommonUtils.getGson().toJson(errorDTO);
        }
        response.getWriter().write(payload);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException,
            ServletException {
        int userId = AuthUtils.getUserId(AuthUtils.extractAuthToken(request));
        Story story = CommonUtils.getGson().fromJson(request.getReader(), Story.class);
        storyService.updateStoryDescription(story, userId);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException,
            ServletException {
        int userId = AuthUtils.getUserId(AuthUtils.extractAuthToken(request));
        int storyId = Integer.parseInt(request.getParameter("id"));
        storyService.deleteStory(storyId, userId);
    }
}
