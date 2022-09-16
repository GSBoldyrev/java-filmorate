package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.SimpleDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class MpaDaoImpl implements SimpleDao<Mpa> {

    private final JdbcTemplate jdbcTemplate;

    public MpaDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Mpa getById(int id) {
        String sqlQuery = "SELECT mpa_id, description FROM mpa_rating WHERE mpa_id = ?";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToMpa, id);
        } catch (DataAccessException e) {
            throw new NotFoundException("Рейтинг по ID " + id + " не найден!");
        }
    }

    @Override
    public List<Mpa> getAll() {
        String sqlQuery = "SELECT mpa_id, description FROM mpa_rating";

        return jdbcTemplate.query(sqlQuery, this::mapRowToMpa);
    }

    private Mpa mapRowToMpa(ResultSet resultSet, int rowNum) throws SQLException {

        return new Mpa(resultSet.getInt("mpa_id"), resultSet.getString("description"));
    }
}
