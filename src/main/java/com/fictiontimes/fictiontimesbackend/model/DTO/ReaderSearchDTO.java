package com.fictiontimes.fictiontimesbackend.model.DTO;

import com.fictiontimes.fictiontimesbackend.model.*;

import java.util.ArrayList;
import java.util.List;

public class ReaderSearchDTO {
    private List<User> writers;
    private List<ReaderStoryDTO> stories;
    private List<SearchEpisodeDTO> episodes;
    private List<Genre> genres;

    public ReaderSearchDTO() {
        writers = new ArrayList<>();
        stories = new ArrayList<>();
        episodes = new ArrayList<>();
        genres = new ArrayList<>();
    }

    public void add(User writer) {
        writers.add(writer);
    }

    public void add(ReaderStoryDTO story) {
        stories.add(story);
    }

    public void add(SearchEpisodeDTO episode) {
        episodes.add(episode);
    }

    public void add(Genre genre) {
        genres.add(genre);
    }
}
