package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmDao extends StandartDao<Film> {

    List<Film> getMostPopular(int count);
}
