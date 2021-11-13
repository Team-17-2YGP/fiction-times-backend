package com.fictiontimes.fictiontimesbackend.model.DTO;

import com.fictiontimes.fictiontimesbackend.model.Writer;

public class WriterDetailsDTO extends Writer {
    private int followerCount;
    private int storyCount;
    private boolean isFollowing;
    private boolean isSubscribedNotifications;

    public WriterDetailsDTO() {
        super();
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public int getStoryCount() {
        return storyCount;
    }

    public void setStoryCount(int storyCount) {
        this.storyCount = storyCount;
    }

    public boolean isFollowing() {
        return isFollowing;
    }

    public void setFollowing(boolean following) {
        isFollowing = following;
    }

    public boolean isSubscribedNotifications() {
        return isSubscribedNotifications;
    }

    public void setSubscribedNotifications(boolean subscribedNotifications) {
        isSubscribedNotifications = subscribedNotifications;
    }
}
