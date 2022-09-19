package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.dao.SimpleDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class FilmDaoImpl implements FilmDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleDao<Mpa> mpaDao;

    public FilmDaoImpl(JdbcTemplate jdbcTemplate, SimpleDao<Mpa> mpaDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.mpaDao = mpaDao;
    }

    @Override
    public Film add(Film film) {
        String sqlQuery = "INSERT INTO films (name, description, release_date, duration, rate, mpa) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sqlQuery, new String[]{"film_id"});
            statement.setString(1, film.getName());
            statement.setString(2, film.getDescription());
            statement.setDate(3, Date.valueOf(film.getReleaseDate()));
            statement.setInt(4, film.getDuration());
            statement.setInt(5, film.getRate());
            statement.setInt(6, film.getMpa().getId());
            return statement;
        }, keyHolder);
        film.setId(keyHolder.getKey().intValue());

        return film;
    }

    @Override
    public Film update(Film film) {
        String sqlQuery = "UPDATE films SET name = ?, description = ?, " +
                "release_date = ?, duration = ?, rate = ?,  mpa = ? " +
                "WHERE film_id = ?";
        int result = jdbcTemplate.update(sqlQuery, film.getName(), film.getDescription(),
                film.getReleaseDate(), film.getDuration(), film.getRate(),
                film.getMpa().getId(), film.getId());
        if (result != 1) {
            throw new NotFoundException("Фильм по ID " + film.getId() + " не найден!");
        }

        return film;
    }

    @Override
    public int delete(int id) {
        String sqlQuery = "DELETE FROM films WHERE film_id = ?";

        return jdbcTemplate.update(sqlQuery, id);
    }

    @Override
    public Film getById(int id) {
        String sqlQuery = "SELECT film_id, name, description, release_date, duration, rate, mpa FROM films " +
                "WHERE film_id = ?";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToFilm, id);
        } catch (DataAccessException e) {
            throw new NotFoundException("Фильм по ID " + id + " не найден!");
        }
    }

    @Override
    public List<Film> getAll() {
        String sqlQuery = "SELECT film_id, name, description, release_date, duration, rate, mpa FROM films";

        return jdbcTemplate.query(sqlQuery, this::mapRowToFilm);
    }

    @Override
    public List<Film> getMostPopular(int count) {
        String sqlQuery = "SELECT film_id, name, description, release_date, duration, rate, mpa " +
                "FROM films ORDER BY rate DESC LIMIT ?";

        return jdbcTemplate.query(sqlQuery, this::mapRowToFilm, count);
    }


    private Film mapRowToFilm(ResultSet resultSet, int rowNum) throws SQLException {
        Film film = new Film(resultSet.getString("name"), resultSet.getString("description"),
                resultSet.getDate("release_date").toLocalDate(), resultSet.getInt("duration"),
                mpaDao.getById(resultSet.getInt("mpa")));
        film.setId(resultSet.getInt("film_id"));
        film.setRate(resultSet.getInt("rate"));

        return film;
    }
}
