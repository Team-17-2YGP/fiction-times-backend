package com.fictiontimes.fictiontimesbackend.model.DTO;

import com.fictiontimes.fictiontimesbackend.model.Episode;
import com.fictiontimes.fictiontimesbackend.model.Story;

import java.util.List;

public class AdminStoryDTO {
    private Story story;
    private List<StoryReviewDTO> storyReviewDTOS;
    private List<Episode> episodes;
    private StoryRatingDTO storyRatingDTO;

    public AdminStoryDTO(Story story, List<StoryReviewDTO> storyReviewDTOS, List<Episode> episodes, StoryRatingDTO storyRatingDTO) {
        this.story = story;
        this.storyReviewDTOS = storyReviewDTOS;
        this.episodes = episodes;
        this.storyRatingDTO = storyRatingDTO;
    }

    public AdminStoryDTO() { }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    public List<StoryReviewDTO> getStoryReviewDTOS() {
        return storyReviewDTOS;
    }

    public void setStoryReviewDTOS(List<StoryReviewDTO> storyReviewDTOS) {
        this.storyReviewDTOS = storyReviewDTOS;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }

    public StoryRatingDTO getStoryRatingDTO() {
        return storyRatingDTO;
    }

    public void setStoryRatingDTO(StoryRatingDTO storyRatingDTO) {
        this.storyRatingDTO = storyRatingDTO;
    }
}
