package com.fictiontimes.fictiontimesbackend.model;

public class ReaderStats {
    int time;
    int episodeCount;
    int writerCount;

    public ReaderStats() {
        this.time = 0;
        this.episodeCount = 0;
        this.writerCount = 0;
    }

    public ReaderStats(int time, int episodeCount, int writerCount) {
        this.time = time;
        this.episodeCount = episodeCount;
        this.writerCount = writerCount;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(int episodeCount) {
        this.episodeCount = episodeCount;
    }

    public int getWriterCount() {
        return writerCount;
    }

    public void setWriterCount(int writerCount) {
        this.writerCount = writerCount;
    }
}
