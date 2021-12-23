package com.fictiontimes.fictiontimesbackend.model;

import java.util.Date;

public class TimeData {
    int readerId;
    int episodeId;
    Date open;
    Date close;
    long duration;

    public TimeData(int readerId, int episodeId, Date open, Date close, long duration) {
        this.readerId = readerId;
        this.episodeId = episodeId;
        this.open = open;
        this.close = close;
        this.duration = duration;
    }

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public int getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(int episodeId) {
        this.episodeId = episodeId;
    }

    public Date getOpen() {
        return open;
    }

    public void setOpen(Date open) {
        this.open = open;
    }

    public Date getClose() {
        return close;
    }

    public void setClose(Date close) {
        this.close = close;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
