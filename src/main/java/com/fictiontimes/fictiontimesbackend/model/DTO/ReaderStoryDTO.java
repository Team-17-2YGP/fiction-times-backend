package com.fictiontimes.fictiontimesbackend.model.DTO;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.model.Genre;
import com.fictiontimes.fictiontimesbackend.model.Story;
import com.fictiontimes.fictiontimesbackend.model.Types.StoryStatus;
import com.fictiontimes.fictiontimesbackend.model.Writer;
import com.fictiontimes.fictiontimesbackend.repository.UserRepository;
import com.fictiontimes.fictiontimesbackend.repository.WriterRepository;
import com.fictiontimes.fictiontimesbackend.service.WriterService;

import java.sql.Timestamp;
import java.util.List;

public class ReaderStoryDTO {
    private int storyId;
    private Writer writer;
    private String title;
    private String description;
    private int likeCount;
    private String coverArtUrl;
    private StoryStatus status;
    private Timestamp releasedDate;
    private List<String> tags;
    private List<Genre> genres;

    public ReaderStoryDTO(Story story) throws DatabaseOperationException {
        WriterService writerService = new WriterService(new WriterRepository(), new UserRepository());
        this.storyId = story.getStoryId();
        this.writer = writerService.getWriterById(story.getUserId());
        this.title = story.getTitle();
        this.description = story.getDescription();
        this.likeCount = story.getLikeCount();
        this.coverArtUrl = story.getCoverArtUrl();
        this.status = story.getStatus();
        this.releasedDate = story.getReleasedDate();
        this.tags = story.getTags();
        this.genres = story.getGenres();
    }

    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
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
}
