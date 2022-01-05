package com.fictiontimes.fictiontimesbackend.model.DTO;

import java.util.List;

public class ReaderHomeDTO {
    private List<ReaderStoryDTO> onLike;
    private List<ReaderStoryDTO> onRead;
    private List<ReaderStoryDTO> onGenre;

    public ReaderHomeDTO(List<ReaderStoryDTO> onLike, List<ReaderStoryDTO> onRead, List<ReaderStoryDTO> onGenre) {
        this.onLike = onLike;
        this.onRead = onRead;
        this.onGenre = onGenre;
    }

    public List<ReaderStoryDTO> getOnLike() {
        return onLike;
    }

    public void setOnLike(List<ReaderStoryDTO> onLike) {
        this.onLike = onLike;
    }

    public List<ReaderStoryDTO> getOnRead() {
        return onRead;
    }

    public void setOnRead(List<ReaderStoryDTO> onRead) {
        this.onRead = onRead;
    }

    public List<ReaderStoryDTO> getOnGenre() {
        return onGenre;
    }

    public void setOnGenre(List<ReaderStoryDTO> onGenre) {
        this.onGenre = onGenre;
    }
}
