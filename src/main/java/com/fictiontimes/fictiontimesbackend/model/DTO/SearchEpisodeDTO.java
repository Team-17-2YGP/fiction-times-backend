package com.fictiontimes.fictiontimesbackend.model.DTO;

import com.fictiontimes.fictiontimesbackend.model.Episode;

public class SearchEpisodeDTO extends Episode {
    private String storyTitle;
    private String coverArtUrl;

    public String getCoverArtUrl() {
        return coverArtUrl;
    }

    public void setCoverArtUrl(String coverArtUrl) {
        this.coverArtUrl = coverArtUrl;
    }

    public String getStoryTitle() {
        return storyTitle;
    }

    public void setStoryTitle(String storyTitle) {
        this.storyTitle = storyTitle;
    }
}
