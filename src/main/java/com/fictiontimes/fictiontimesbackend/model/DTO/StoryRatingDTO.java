package com.fictiontimes.fictiontimesbackend.model.DTO;

public class StoryRatingDTO {
    private int reviewerCount;
    private float averageRating;

    public StoryRatingDTO() {
    }

    public StoryRatingDTO(int reviewerCount, float averageRating) {
        this.reviewerCount = reviewerCount;
        this.averageRating = averageRating;
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
}
