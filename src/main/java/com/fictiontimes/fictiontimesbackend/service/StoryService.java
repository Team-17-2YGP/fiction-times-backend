package com.fictiontimes.fictiontimesbackend.service;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.model.Genre;
import com.fictiontimes.fictiontimesbackend.model.Story;
import com.fictiontimes.fictiontimesbackend.repository.GenreRepository;
import com.fictiontimes.fictiontimesbackend.repository.StoryRepository;

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
}
