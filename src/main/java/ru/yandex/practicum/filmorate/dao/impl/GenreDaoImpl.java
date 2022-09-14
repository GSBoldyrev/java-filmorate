package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@Component
public class GenreDaoImpl implements GenreDao {

    private final JdbcTemplate jdbcTemplate;

    public GenreDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genre getById(int id) {
        String sqlQuery = "select genre_id, description from genres where genre_id = ?";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToGenre, id);
        } catch (DataAccessException e) {
            throw new NotFoundException("Жанр по ID " + id + " не найден!");
        }
    }

    @Override
    public List<Genre> getAll() {
        String sqlQuery = "select genre_id, description from genres";

        return jdbcTemplate.query(sqlQuery, this::mapRowToGenre);
    }

    @Override
    public List<Genre> getForFilm(int filmId) {
        String sqlQuery = "select genre_id, description from genres where genre_id in " +
                "(select genre_id from movie_genre where film_id = ?)";

        return jdbcTemplate.query(sqlQuery, this::mapRowToGenre, filmId);
    }

    @Override
    public void setForFilm(Set<Integer> genres, int filmId) {
        String sqlQueryDelete = "delete from movie_genre where film_id = ?";
        jdbcTemplate.update(sqlQueryDelete, filmId);
        for (Integer id: genres) {
            String sqlQuery = "insert into movie_genre (film_id, genre_id) values (?, ?)";
            jdbcTemplate.update(sqlQuery, filmId, id);
        }
    }

    private Genre mapRowToGenre (ResultSet resultSet, int rowNum) throws SQLException {

        return new Genre(resultSet.getInt("genre_id"), resultSet.getString("description"));
    }
}
