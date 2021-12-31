package com.fictiontimes.fictiontimesbackend.model.DTO;

import com.fictiontimes.fictiontimesbackend.model.Episode;
import com.fictiontimes.fictiontimesbackend.model.Story;

import java.util.Date;

public class ReaderEpisodeDTO extends Episode {
    private boolean finishedReading;
    private boolean isBookmarked;
    private Story story;

    public ReaderEpisodeDTO() {}

    public ReaderEpisodeDTO(int episodeId, int storyId, int episodeNumber, String title,
                            String description, int readCount, Date uploadedAt, String content,
                            boolean finishedReading, boolean isBookmarked, Story story) {
        super(episodeId, storyId, episodeNumber, title, description, readCount, uploadedAt, content);
        this.finishedReading = finishedReading;
        this.isBookmarked = isBookmarked;
        this.story = story;
    }

    public boolean isFinishedReading() {
        return finishedReading;
    }

    public void setFinishedReading(boolean finishedReading) {
        this.finishedReading = finishedReading;
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
