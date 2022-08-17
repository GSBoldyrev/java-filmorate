package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.List;

@Service
@Slf4j
public class FilmService {

    private final FilmStorage films;

    @Autowired
    public FilmService(FilmStorage films) {
        this.films = films;
    }

    public Film add(Film film) {
        return films.add(film);
    }

    public Film update(Film film) {
        return films.update(film);
    }

    public Film delete(Film film) {
        return films.delete(film);
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

    public Film addLike(Film film, User user) {
        film.getUsersLiked().add(user.getId());
        film.setLikes(film.getLikes() + 1);
        films.update(film);
        log.info("Пользователь " + user.getName() + " лайкнул фильм " + film.getName());
        return film;
    }

    public Film removeLike(Film film, User user) {
        film.getUsersLiked().remove(user.getId());
        film.setLikes(film.getLikes() - 1);
        films.update(film);
        log.info("Пользователь " + user.getName() + " удалил свой лайк у фильма " + film.getName());
        return film;
    }
}
