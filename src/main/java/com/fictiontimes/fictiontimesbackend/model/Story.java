package com.fictiontimes.fictiontimesbackend.model;

import com.fictiontimes.fictiontimesbackend.model.Types.StoryStatus;

import java.sql.Timestamp;

public class Story {
    private int storyId;
    private int userId;
    private String title;
    private String description;
    private int likeCount;
    private String coverArtUrl;
    private StoryStatus status;
    private Timestamp releasedDate;
    private String tags; // TODO: create tag model class
    private String genres; // TODO: create genre model class

    public Story(int storyId, int userId, String title, String description, int likeCount, String coverArtUrl,
                 StoryStatus status,
                 Timestamp releasedDate, String tags, String genres) {
        this.storyId = storyId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.likeCount = likeCount;
        this.coverArtUrl = coverArtUrl;
        this.status = status;
        this.releasedDate = releasedDate;
        this.tags = tags;
        this.genres = genres;
    }

    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getCoverArtUrl() {
        return coverArtUrl;
    }

    public void setCoverArtUrl(String coverArtUrl) {
        this.coverArtUrl = coverArtUrl;
    }

    public StoryStatus getStatus() {
        return status;
    }

    public void setStatus(StoryStatus status) {
        this.status = status;
    }

    public Timestamp getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(Timestamp releasedDate) {
        this.releasedDate = releasedDate;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }
}
