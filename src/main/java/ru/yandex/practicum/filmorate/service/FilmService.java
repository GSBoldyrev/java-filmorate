package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

import static ru.yandex.practicum.filmorate.misc.Validator.validate;

@Service
@Slf4j
public class FilmService {

    private final FilmDao films;

    @Autowired
    public FilmService(FilmDao films) {
        this.films = films;
    }

    public Film add(Film film) {
        validate(film);
        return films.add(film);
    }

    public Film update(Film film) {
        validate(film);
        return films.update(film);
    }

    public int delete(int id) {
        return films.delete(id);
    }

    public Film getById(int id) {
        return films.getById(id);
    }

    public List<Film> getAll() {
        return films.getAll();
    }

    public List<Film> getMostPopular(int count) {
        return films.getMostPopular(count);
    }

    public int addLike(int filmId, int userId) {
        Film film = films.getById(filmId);
        film.setRate(film.getRate() + 1);
        films.update(film);
        return films.addLike(filmId, userId);
    }

    public int removeLike(int filmId, int userId) {
        Film film = films.getById(filmId);
        film.setRate(film.getRate() - 1);
        films.update(film);
        return films.removeLike(filmId, userId);
    }
}
