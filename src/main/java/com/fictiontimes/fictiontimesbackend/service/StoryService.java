package com.fictiontimes.fictiontimesbackend.service;

import com.fictiontimes.fictiontimesbackend.model.Genre;
import com.fictiontimes.fictiontimesbackend.model.Story;
import com.fictiontimes.fictiontimesbackend.repository.GenreRepository;
import com.fictiontimes.fictiontimesbackend.repository.StoryRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class StoryService {
    private final StoryRepository storyRepository;
    private final GenreRepository genreRepository;

    public StoryService(StoryRepository storyRepository, GenreRepository genreRepository){
        this.storyRepository = storyRepository;
        this.genreRepository = genreRepository;
    }

    public Story createNewStory(Story story) throws SQLException, IOException, ClassNotFoundException {
        story = storyRepository.createNewStory(story);
        return story;
    }

    public Genre createNewGenre(Genre genre) throws SQLException, IOException, ClassNotFoundException {
        return genreRepository.createNewGenre(genre);
    }

    public List<Genre> getGenreList() throws SQLException, IOException, ClassNotFoundException {
        return genreRepository.getGenreList();
    }
}
