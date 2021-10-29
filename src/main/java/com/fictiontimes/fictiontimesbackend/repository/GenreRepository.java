package com.fictiontimes.fictiontimesbackend.repository;

import com.fictiontimes.fictiontimesbackend.model.Genre;
import com.fictiontimes.fictiontimesbackend.model.WriterApplicant;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreRepository {
    private PreparedStatement statement;

    public List<Genre> getGenreList() throws SQLException, IOException, ClassNotFoundException {
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
    }

    public Genre createNewGenre(Genre genre) throws SQLException, IOException, ClassNotFoundException {
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
    }

    public void deleteGenreById(int genreId) throws SQLException, IOException, ClassNotFoundException {
        statement = DBConnection.getConnection().prepareStatement(
                "DELETE FROM genre WHERE (genreId) = ? "
        );
        statement.getUpdateCount();
        statement.executeUpdate();
    }
}
