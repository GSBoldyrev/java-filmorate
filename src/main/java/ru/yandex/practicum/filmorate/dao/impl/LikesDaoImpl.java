package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.ManyToManyDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;

@Component
public class LikesDaoImpl implements ManyToManyDao {
    private final JdbcTemplate jdbcTemplate;

    public LikesDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int add(int filmId, int userId) {
        try {
            String sqlQuery = "INSERT INTO movie_likes (film_id, user_id) VALUES (?, ?)";
            return jdbcTemplate.update(sqlQuery, filmId, userId);
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException("передан неверный идентификатор!");
        }
    }

    @Override
    public int remove(int filmId, int userId) {
        String sqlQuery = "DELETE FROM movie_likes WHERE film_id = ? AND user_id = ?";
        int result = jdbcTemplate.update(sqlQuery, filmId, userId);
        if (result != 1) {
            throw new NotFoundException("передан неверный идентификатор!");
        }
        return result;
    }
}
