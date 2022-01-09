package com.fictiontimes.fictiontimesbackend.model;

import com.fictiontimes.fictiontimesbackend.model.Types.StoryStatus;
import jakarta.servlet.http.Part;

import java.sql.Timestamp;
import java.util.List;

public class Story {
    private int storyId;
    private int userId;
    private String title;
    private String description;
    private int likeCount;
    private String coverArtUrl;
    private Part coverArt;
    private StoryStatus status;
    private Timestamp releasedDate;
    private List<String> tags;
    private List<Genre> genres;
    private int reviewerCount;
    private float averageRating;
    private int readCount;

    public Story(int storyId, int userId, String title, String description, int likeCount, String coverArtUrl,
                 Part coverArt, StoryStatus status,
                 Timestamp releasedDate, List<String> tags, List<Genre> genres) {
        this.storyId = storyId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.likeCount = likeCount;
        this.coverArtUrl = coverArtUrl;
        this.coverArt = coverArt;
        this.status = status;
        this.releasedDate = releasedDate;
        this.tags = tags;
        this.genres = genres;
    }

    public Story(int storyId, int userId, String title, String description, int likeCount, String coverArtUrl, Part coverArt, StoryStatus status, Timestamp releasedDate, List<String> tags, List<Genre> genres, int reviewerCount, float averageRating) {
        this.storyId = storyId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.likeCount = likeCount;
        this.coverArtUrl = coverArtUrl;
        this.coverArt = coverArt;
        this.status = status;
        this.releasedDate = releasedDate;
        this.tags = tags;
        this.genres = genres;
        this.reviewerCount = reviewerCount;
        this.averageRating = averageRating;
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

    public Part getCoverArt() {
        return coverArt;
    }

    public void setCoverArt(Part coverArt) {
        this.coverArt = coverArt;
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public int getReviewerCount() {
        return reviewerCount;
    }

    public void setReviewerCount(int reviewerCount) {
        this.reviewerCount = reviewerCount;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }
}
