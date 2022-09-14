package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Set;

public interface GenreDao {


    Genre getById(int id);

    List<Genre> getAll();

    List<Genre> getForFilm(int filmId);

    void setForFilm(Set<Integer> genres, int filmId);
}
