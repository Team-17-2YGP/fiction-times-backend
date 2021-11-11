package com.fictiontimes.fictiontimesbackend.repository;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.model.Genre;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreRepository {
    private PreparedStatement statement;

    public List<Genre> getGenreList() throws DatabaseOperationException {
        try {
            List<Genre> genreListList = new ArrayList<>();
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT * FROM genre"
            );
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Genre genre = new Genre(
                        resultSet.getInt("genreId"),
                        resultSet.getString("genreName")
                );
                genreListList.add(genre);
            }
            return genreListList;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public Genre createNewGenre(Genre genre) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "INSERT INTO genre (genreName)" +
                            "VALUES(?)",
                    statement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, genre.getGenreName());
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                genre.setGenreId(resultSet.getInt(1));
            }
            return genre;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public void deleteGenreById(int genreId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "DELETE FROM genre WHERE (genreId) = ? "
            );
            statement.setInt(1, genreId);
            statement.executeUpdate();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }
}
