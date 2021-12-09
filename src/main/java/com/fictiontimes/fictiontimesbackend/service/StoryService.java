package com.fictiontimes.fictiontimesbackend.service;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.exception.NoSuchObjectFoundException;
import com.fictiontimes.fictiontimesbackend.model.DTO.ReaderStoryDTO;
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

    public ReaderStoryDTO getStoryDetailsByStoryId(int storyId) throws DatabaseOperationException{
        Story story = storyRepository.getStoryById(storyId);
        return  new ReaderStoryDTO(story);
    }

    public List<Episode> getEpisodeListByStoryId(int storyId) throws DatabaseOperationException{
        return storyRepository.getEpisodeListByStoryId(storyId);
    }

    public void saveEpisode(Episode episode, int userId) throws DatabaseOperationException, NoSuchObjectFoundException {
        Story story = getStoryById(episode.getStoryId(), userId);
        if (story == null) {
            throw new NoSuchObjectFoundException("Story object by the received story id doesn't exist");
        }
        storyRepository.saveEpisode(episode);
    }
}
