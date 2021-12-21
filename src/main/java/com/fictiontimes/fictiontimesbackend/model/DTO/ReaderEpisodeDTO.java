package com.fictiontimes.fictiontimesbackend.model.DTO;

import com.fictiontimes.fictiontimesbackend.model.Episode;
import com.fictiontimes.fictiontimesbackend.model.Story;

import java.util.Date;

public class ReaderEpisodeDTO extends Episode {
    private boolean isBookmarked;
    private Story story;

    public ReaderEpisodeDTO() {}

    public ReaderEpisodeDTO(int episodeId, int storyId, int episodeNumber, String title, String description, int readCount, Date uploadedAt, String content, boolean isBookmarked, Story story) {
        super(episodeId, storyId, episodeNumber, title, description, readCount, uploadedAt, content);
        this.isBookmarked = isBookmarked;
        this.story = story;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }
}
