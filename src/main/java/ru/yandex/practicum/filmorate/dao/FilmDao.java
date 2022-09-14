package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmDao {

    Film add(Film film);

    Film update(Film film);

    int delete(int id);

    Film getById(int id);

    List<Film> getAll();

    List<Film> getMostPopular(int count);

    int addLike(int filmId, int userId);

    int removeLike(int filmId, int userId);

}
