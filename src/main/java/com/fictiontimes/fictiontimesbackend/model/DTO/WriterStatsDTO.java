package com.fictiontimes.fictiontimesbackend.model.DTO;

import com.fictiontimes.fictiontimesbackend.model.Story;

import java.util.List;

public class WriterStatsDTO {
    private int followersAllTime;
    private int followersLastYear;
    private int followers30Days;
    private int followers7Days;
    private int likesAllTime;
    private int likesLastYear;
    private int likes30Days;
    private int likes7Days;
    private int readCountAllTime;
    private int readCountLastYear;
    private int readCount30Days;
    private int readCount7Days;
    private List<Story> stories;

    public WriterStatsDTO() {
    }

    public WriterStatsDTO(int followersAllTime, int followersLastYear, int followers30Days, int followers7Days,
                          int likesAllTime, int likesLastYear, int likes30Days, int likes7Days,
                          int readCountAllTime, int readCountLastYear, int readCount30Days, int readCount7Days) {
        this.followersAllTime = followersAllTime;
        this.followersLastYear = followersLastYear;
        this.followers30Days = followers30Days;
        this.followers7Days = followers7Days;
        this.likesAllTime = likesAllTime;
        this.likesLastYear = likesLastYear;
        this.likes30Days = likes30Days;
        this.likes7Days = likes7Days;
        this.readCountAllTime = readCountAllTime;
        this.readCountLastYear = readCountLastYear;
        this.readCount30Days = readCount30Days;
        this.readCount7Days = readCount7Days;
    }

    public int getFollowersAllTime() {
        return followersAllTime;
    }

    public void setFollowersAllTime(int followersAllTime) {
        this.followersAllTime = followersAllTime;
    }

    public int getFollowersLastYear() {
        return followersLastYear;
    }

    public void setFollowersLastYear(int followersLastYear) {
        this.followersLastYear = followersLastYear;
    }

    public int getFollowers30Days() {
        return followers30Days;
    }

    public void setFollowers30Days(int followers30Days) {
        this.followers30Days = followers30Days;
    }

    public int getFollowers7Days() {
        return followers7Days;
    }

    public void setFollowers7Days(int followers7Days) {
        this.followers7Days = followers7Days;
    }

    public int getLikesAllTime() {
        return likesAllTime;
    }

    public void setLikesAllTime(int likesAllTime) {
        this.likesAllTime = likesAllTime;
    }

    public int getLikesLastYear() {
        return likesLastYear;
    }

    public void setLikesLastYear(int likesLastYear) {
        this.likesLastYear = likesLastYear;
    }

    public int getLikes30Days() {
        return likes30Days;
    }

    public void setLikes30Days(int likes30Days) {
        this.likes30Days = likes30Days;
    }

    public int getLikes7Days() {
        return likes7Days;
    }

    public void setLikes7Days(int likes7Days) {
        this.likes7Days = likes7Days;
    }

    public int getReadCountAllTime() {
        return readCountAllTime;
    }

    public void setReadCountAllTime(int readCountAllTime) {
        this.readCountAllTime = readCountAllTime;
    }

    public int getReadCountLastYear() {
        return readCountLastYear;
    }

    public void setReadCountLastYear(int readCountLastYear) {
        this.readCountLastYear = readCountLastYear;
    }

    public int getReadCount30Days() {
        return readCount30Days;
    }

    public void setReadCount30Days(int readCount30Days) {
        this.readCount30Days = readCount30Days;
    }

    public int getReadCount7Days() {
        return readCount7Days;
    }

    public void setReadCount7Days(int readCount7Days) {
        this.readCount7Days = readCount7Days;
    }

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }
}
