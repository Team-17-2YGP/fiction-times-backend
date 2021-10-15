package com.fictiontimes.fictiontimesbackend.controller.writer;

import com.fictiontimes.fictiontimesbackend.exception.InvalidTokenException;
import com.fictiontimes.fictiontimesbackend.exception.TokenExpiredException;
import com.fictiontimes.fictiontimesbackend.exception.TokenNotFoundException;
import com.fictiontimes.fictiontimesbackend.model.DTO.ErrorDTO;
import com.fictiontimes.fictiontimesbackend.model.Story;
import com.fictiontimes.fictiontimesbackend.model.Types.StoryStatus;
import com.fictiontimes.fictiontimesbackend.repository.StoryRepository;
import com.fictiontimes.fictiontimesbackend.service.StoryService;
import com.fictiontimes.fictiontimesbackend.utils.AuthUtils;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

@WebServlet("/writer/story")
@MultipartConfig
public class PostStoryServlet extends HttpServlet {

    private final StoryService storyService = new StoryService(new StoryRepository());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String payload;
        Story story = null;
        try {
            int userId = AuthUtils.getUserId(AuthUtils.extractAuthToken(request));
            String title = request.getParameter("title");
            String reqStatus = request.getParameter("status");
            String description = request.getParameter("description");
            StoryStatus status = StoryStatus.valueOf(request.getParameter("status"));
            // Set current time as the releasedDate
            Timestamp releasedDate = new Timestamp(new Date().getTime());
            //TODO: get coverArt part file and save it in the S3 bucket
            String genres = request.getParameter("genres");
            String tags = request.getParameter("tags");

            story = new Story(0,userId,title, description, 0, "someCoverArt.s3bucket.com",
                    status, releasedDate, tags, genres);

            storyService.createNewStory(story);

            payload = CommonUtils.getGson().toJson(story);
            response.setStatus(HttpServletResponse.SC_CREATED);

        } catch (SQLException | ClassNotFoundException | TokenNotFoundException | InvalidTokenException | TokenExpiredException e) {
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
        response.setContentType("application/json");
        response.getWriter().write(payload);
    }
}
