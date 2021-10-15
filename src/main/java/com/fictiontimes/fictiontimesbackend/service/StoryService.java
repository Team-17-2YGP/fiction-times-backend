package com.fictiontimes.fictiontimesbackend.service;

import com.fictiontimes.fictiontimesbackend.model.Story;
import com.fictiontimes.fictiontimesbackend.repository.StoryRepository;

import java.io.IOException;
import java.sql.SQLException;

public class StoryService {
    private final StoryRepository storyRepository;

    public StoryService(StoryRepository storyRepository){
        this.storyRepository = storyRepository;
    }

    public Story createNewStory(Story story) throws SQLException, IOException, ClassNotFoundException {
        story = storyRepository.createNewStory(story);
        return story;
    }
}
