package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.ManyToManyDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;

@Component
public class FriendsDaoImpl implements ManyToManyDao {

    private final JdbcTemplate jdbcTemplate;

    public FriendsDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int add(int userId, int friendId) {
        try {
            String sqlQuery = "INSERT INTO friends (user_id, friend_id) VALUES (?, ?)";
            return jdbcTemplate.update(sqlQuery, userId, friendId);
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException("передан неверный идентификатор!");
        }
    }

    @Override
    public int remove(int userId, int friendId) {
        String sqlQuery = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";

        return jdbcTemplate.update(sqlQuery, userId, friendId);
    }
}
