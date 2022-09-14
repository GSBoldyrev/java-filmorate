package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User add(User user) {
        String sqlQuery = "insert into users (e_mail, login, name, birthday) values (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sqlQuery, new String[]{"user_id"});
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getName());
            statement.setDate(4, Date.valueOf(user.getBirthday()));
            return statement;
        }, keyHolder);
        user.setId(keyHolder.getKey().intValue());

        return user;
    }

    @Override
    public User update(User user) {
        String sqlQuery = "update users set e_mail = ?, login = ?, name = ?, birthday = ? where user_id = ?";
        int result = jdbcTemplate.update(sqlQuery, user.getEmail(), user.getLogin(),
                user.getName(), user.getBirthday(), user.getId());
        if (result != 1) {
            throw new NotFoundException("Пользователь по ID " + user.getId() + " не найден!");
        }

        return user;
    }

    @Override
    public int delete(int id) {
        String sqlQuery = "delete from users where user_id = ?";

        return jdbcTemplate.update(sqlQuery, id);
    }

    @Override
    public User getById(int id) {
        String sqlQuery = "select user_id, e_mail, login, name, birthday from users where user_id = ?";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToUser, id);
        } catch (DataAccessException e) {
            throw new NotFoundException("Пользователь по ID " + id + " не найден!");
        }
    }

    @Override
    public List<User> getAll() {
        String sqlQuery = "select user_id, e_mail, login, name, birthday from users";

        return jdbcTemplate.query(sqlQuery, this::mapRowToUser);
    }

    @Override
    public int addFriend(int userId, int friendId) {
        try {
            String sqlQuery = "insert into friends (user_id, friend_id) values (?, ?)";
            return jdbcTemplate.update(sqlQuery, userId, friendId);
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException("передан неверный идентификатор!");
        }
    }

    @Override
    public int removeFriend(int userId, int friendId) {
        String sqlQuery = "delete from friends where user_id = ? and friend_id = ?";

        return jdbcTemplate.update(sqlQuery, userId, friendId);
    }

    @Override
    public List<User> getFriends (int userId) {
        String sqlQuery = "select user_id, e_mail, login, name, birthday from users " +
                "where user_id in (select friend_id from friends where user_id = ?)";

            return jdbcTemplate.query(sqlQuery, this::mapRowToUser, userId);
    }

    @Override
    public List<User> getMutualFriends (int userId, int friendId) {
        String sqlQuery = "select user_id, e_mail, login, name, birthday from users " +
                "where user_id in " +
                "(select friend_id from friends where user_id = ? and friend_id not in (?, ?) and " +
                "friend_id in (select friend_id from friends where user_id = ?))";

            return jdbcTemplate.query(sqlQuery, this::mapRowToUser, userId, friendId, userId, friendId);
    }

    private User mapRowToUser (ResultSet resultSet, int rowNum) throws SQLException {
        User user = new User(resultSet.getString("e_mail"),
                resultSet.getString("login"), resultSet.getDate("birthday").toLocalDate());
        user.setName(resultSet.getString("name"));
        user.setId(resultSet.getInt("user_id"));

        return user;
    }
}
