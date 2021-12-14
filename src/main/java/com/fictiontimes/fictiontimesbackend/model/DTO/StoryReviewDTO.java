package com.fictiontimes.fictiontimesbackend.model.DTO;

import com.fictiontimes.fictiontimesbackend.model.Reader;

import java.sql.Timestamp;

public class StoryReviewDTO {
    private int storyId;
    private Reader reader;
    private int rating;
    private String review;
    private Timestamp timestamp;

    public StoryReviewDTO() {
    }

    public StoryReviewDTO(int storyId, Reader reader, int rating, String review, Timestamp timestamp) {
        this.storyId = storyId;
        this.reader = reader;
        this.rating = rating;
        this.review = review;
        this.timestamp = timestamp;
    }

    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
