package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.dao.MpaDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FilmDaoImpl implements FilmDao {

    private final JdbcTemplate jdbcTemplate;
    private final MpaDao mpaDao;
    private final GenreDao genreDao;

    public FilmDaoImpl(JdbcTemplate jdbcTemplate, MpaDao mpaDao, GenreDao genreDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.mpaDao = mpaDao;
        this.genreDao = genreDao;
    }

    @Override
    public Film add(Film film) {
        String sqlQuery = "insert into films (name, description, release_date, duration, rate, mpa) " +
                "values (?, ?, ?, ?, ?, ?)";
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
        populateGenres(film);

        return film;
    }

    @Override
    public Film update(Film film) {
        String sqlQuery = "update films set name = ?, description = ?, " +
                "release_date = ?, duration = ?, rate = ?,  mpa = ? " +
                "where film_id = ?";
        int result = jdbcTemplate.update(sqlQuery, film.getName(), film.getDescription(),
                film.getReleaseDate(), film.getDuration(), film.getRate(),
                film.getMpa().getId(), film.getId());
        if (result != 1) {
            throw new NotFoundException("Фильм по ID " + film.getId() + " не найден!");
        }
        populateGenres(film);

        return film;
    }

    @Override
    public int delete(int id) {
        String sqlQuery = "delete  from films where film_id = ?";

        return jdbcTemplate.update(sqlQuery, id);
    }

    @Override
    public Film getById(int id) {
        String sqlQuery = "select film_id, name, description, release_date, duration, rate, mpa from films where film_id = ?";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToFilm, id);
        } catch (DataAccessException e) {
            throw new NotFoundException("Фильм по ID " + id + " не найден!");
        }
    }

    @Override
    public List<Film> getAll() {
        String sqlQuery = "select film_id, name, description, release_date, duration, rate, mpa from films";

        return jdbcTemplate.query(sqlQuery, this::mapRowToFilm);
    }

    @Override
    public List<Film> getMostPopular(int count) {
        String sqlQuery = "select film_id, name, description, release_date, duration, rate, mpa " +
                "from films order by rate desc limit ?";

        return jdbcTemplate.query(sqlQuery, this::mapRowToFilm, count);
    }

    @Override
    public int addLike(int filmId, int userId) {
        try {
            String sqlQuery = "insert into movie_likes (film_id, user_id) values (?, ?)";
            return jdbcTemplate.update(sqlQuery, filmId, userId);
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException("передан неверный идентификатор!");
        }
    }

    @Override
    public int removeLike(int filmId, int userId) {
        String sqlQuery = "delete from movie_likes where film_id = ? and user_id = ?";
        int result = jdbcTemplate.update(sqlQuery, filmId, userId);
        if (result != 1) {
            throw new NotFoundException("передан неверный идентификатор!");
        }
        return result;
    }

    private Film mapRowToFilm (ResultSet resultSet, int rowNum) throws SQLException {
        Film film = new Film(resultSet.getString("name"), resultSet.getString("description"),
                resultSet.getDate("release_date").toLocalDate(), resultSet.getInt("duration"),
                mpaDao.getById(resultSet.getInt("mpa")));
        film.setId(resultSet.getInt("film_id"));
        film.setRate(resultSet.getInt("rate"));
        film.setGenres(genreDao.getForFilm(film.getId()));

        return film;
    }

    private void populateGenres(Film film) {
        List<Genre> genres = film.getGenres().stream().distinct().collect(Collectors.toList());
        film.setGenres(genres);
        Set<Integer> genresId = new HashSet<>();
        for (Genre genre: film.getGenres()) {
            genresId.add(genre.getId());
        }
        genreDao.setForFilm(genresId, film.getId());
    }
}
