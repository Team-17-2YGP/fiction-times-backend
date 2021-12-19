package com.fictiontimes.fictiontimesbackend.service;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.exception.NoSuchObjectFoundException;
import com.fictiontimes.fictiontimesbackend.exception.UnauthorizedActionException;
import com.fictiontimes.fictiontimesbackend.model.DTO.ReaderStoryDTO;
import com.fictiontimes.fictiontimesbackend.model.DTO.StoryReviewDTO;
import com.fictiontimes.fictiontimesbackend.model.Episode;
import com.fictiontimes.fictiontimesbackend.model.Genre;
import com.fictiontimes.fictiontimesbackend.model.Story;
import com.fictiontimes.fictiontimesbackend.repository.GenreRepository;
import com.fictiontimes.fictiontimesbackend.repository.StoryRepository;
import jakarta.servlet.ServletException;

import java.util.ArrayList;
import java.util.List;

public class StoryService {
    private final StoryRepository storyRepository;
    private final GenreRepository genreRepository;

    public StoryService(StoryRepository storyRepository, GenreRepository genreRepository){
        this.storyRepository = storyRepository;
        this.genreRepository = genreRepository;
    }

    public Story createNewStory(Story story) throws DatabaseOperationException {
        story = storyRepository.createNewStory(story);
        return story;
    }

    /** If a userId is passed, check if the story belong to that user (writer), if not return null*/
    public Story getStoryById(int storyId, int userId) throws DatabaseOperationException {
        Story story = storyRepository.getStoryById(storyId);
        if(story!=null && story.getUserId() == userId){
            return story;
        }
        return null;
    }
    public Story getStoryById(int storyId) throws DatabaseOperationException {
        return storyRepository.getStoryById(storyId);
    }

    /** Get stories created by the user (writer) */
    public List<Story> getStoryList(int userId) throws DatabaseOperationException {
        return storyRepository.getStoryListByUserId(userId);
    }

    public Genre createNewGenre(Genre genre) throws DatabaseOperationException {
        return genreRepository.createNewGenre(genre);
    }

    public List<Genre> getGenreList() throws DatabaseOperationException {
        return genreRepository.getGenreList();
    }

    public void deleteGenreById(int genreId) throws DatabaseOperationException {
        genreRepository.deleteGenreById(genreId);
    }

    public List<ReaderStoryDTO> getRecentlyReleasedStories(int limit) throws ServletException {
        List<Story> storyList = storyRepository.getRecentlyReleasedStories(limit);
        List<ReaderStoryDTO> readerStoryDTOList = new ArrayList<>();
        for (Story story: storyList) {
            readerStoryDTOList.add(new ReaderStoryDTO(story));
        }
        return readerStoryDTOList;
    }

    public ReaderStoryDTO getStoryDetails(int readerId, int storyId) throws DatabaseOperationException{
        Story story = storyRepository.getStoryById(storyId);
        ReaderStoryDTO storyDetails = new ReaderStoryDTO(story);
        storyDetails.setLiked(storyRepository.isLikedStory(readerId, storyId));
        return  storyDetails;
    }

    public List<Episode> getEpisodeListByStoryId(int storyId) throws DatabaseOperationException{
        return storyRepository.getEpisodeListByStoryId(storyId);
    }

    /** Check if the story belong to that user (writer), if not throw unauthorized*/
    public List<Episode> getEpisodeListByStoryId(int storyId, int writerId) throws ServletException {
        Story story = getStoryById(storyId);
        if(story == null){
            throw new NoSuchObjectFoundException("Invalid story id");
        } else if(story.getUserId() == writerId) {
            return storyRepository.getEpisodeListByStoryId(storyId);
        } else {
            throw new UnauthorizedActionException("Story does not belong to the writer");
        }
    }

    public List<StoryReviewDTO> getStoryReviewListByStoryId(int storyId, int writerId, int limit, int offset) throws ServletException {
        Story story = getStoryById(storyId);
        if(story == null){
            throw new NoSuchObjectFoundException("Invalid story id");
        } else if(story.getUserId() == writerId) {
            return storyRepository.getStoryReviewList(storyId, limit, offset);
        } else {
            throw new UnauthorizedActionException("Story does not belong to the writer");
        }
    }

    public void updateStoryDescription(Story story, int writerId) throws ServletException {
        Story matchedStory = getStoryById(story.getStoryId());
        if(matchedStory == null){
            throw new NoSuchObjectFoundException("Invalid story id");
        } else if(matchedStory.getUserId() == writerId) {
            storyRepository.updateStoryDescription(story);
        } else {
            throw new UnauthorizedActionException("Story does not belong to the writer");
        }
    }

    public void deleteStory(int storyId, int writerId) throws ServletException {
        Story story = getStoryById(storyId);
        if(story == null){
            throw new NoSuchObjectFoundException("Invalid story id");
        } else if(story.getUserId() == writerId) {
            storyRepository.deleteStory(storyId);
        } else {
            throw new UnauthorizedActionException("Story does not belong to the writer");
        }
    }

    public void saveEpisode(Episode episode, int userId) throws DatabaseOperationException, NoSuchObjectFoundException {
        Story story = getStoryById(episode.getStoryId(), userId);
        if (story == null) {
            throw new NoSuchObjectFoundException("Story object by the received story id doesn't exist");
        }
        storyRepository.saveEpisode(episode);
    }

    public String getEpisodeContentByEpisodeId(int episodeId) throws DatabaseOperationException{
        return storyRepository.getEpisodeContentByEpisodeId(episodeId);
    }
}
