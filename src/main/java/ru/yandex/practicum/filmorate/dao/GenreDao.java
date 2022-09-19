package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Set;

public interface GenreDao extends SimpleDao<Genre> {

    List<Genre> getForFilm(int filmId);

    void setForFilm(Set<Integer> genres, int filmId);
}
