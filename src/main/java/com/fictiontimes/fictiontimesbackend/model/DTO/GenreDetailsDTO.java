package com.fictiontimes.fictiontimesbackend.model.DTO;

public class GenreDetailsDTO {
    private int genreId;
    private boolean isLiked;
    private String genreName;

    public GenreDetailsDTO(int genreId, boolean isLiked, String genreName) {
        this.genreId = genreId;
        this.isLiked = isLiked;
        this.genreName = genreName;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}
