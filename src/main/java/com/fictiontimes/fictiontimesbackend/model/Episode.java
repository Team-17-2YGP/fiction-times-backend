package com.fictiontimes.fictiontimesbackend.model;

import java.util.Date;

public class Episode {
    private int episodeId;
    private int storyId;
    private int episodeNumber;
    private String title;
    private String description;
    private int readCount;
    private Date uploadedAt;
    private String content;

    public Episode(int storyId, int episodeNumber, String title, String description, int readCount, Date uploadedAt, String content) {
        this.storyId = storyId;
        this.episodeNumber = episodeNumber;
        this.title = title;
        this.description = description;
        this.readCount = readCount;
        this.uploadedAt = uploadedAt;
        this.content = content;
    }

    public int getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(int episodeId) {
        this.episodeId = episodeId;
    }

    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public Date getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Date uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}
