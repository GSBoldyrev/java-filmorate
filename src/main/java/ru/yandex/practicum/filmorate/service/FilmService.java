package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.dao.ManyToManyDao;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.misc.Validator.validate;

@Service
@Slf4j
public class FilmService {

    private final FilmDao filmDao;
    private final ManyToManyDao likesDao;

    private final GenreDao genreDao;

    @Autowired
    public FilmService(FilmDao films, @Qualifier("likesDaoImpl") ManyToManyDao likes, GenreDao genre) {
        this.filmDao = films;
        this.likesDao = likes;
        this.genreDao = genre;
    }

    public Film add(Film film) {
        validate(film);
        Film addedFilm = filmDao.add(film);
        populateGenres(addedFilm);
        return addedFilm;
    }

    public Film update(Film film) {
        validate(film);
        Film updatedFilm = filmDao.update(film);
        populateGenres(updatedFilm);
        return updatedFilm;
    }

    public int delete(int id) {
        return filmDao.delete(id);
    }

    public Film getById(int id) {
        Film film = filmDao.getById(id);
        film.setGenres(genreDao.getForFilm(id));
        return film;
    }

    public List<Film> getAll() {
        List<Film> films = filmDao.getAll();
        for (Film film: films) {
            film.setGenres(genreDao.getForFilm(film.getId()));
        }
        return films;
    }

    public List<Film> getMostPopular(int count) {
        List<Film> films = filmDao.getMostPopular(count);
        for (Film film: films) {
            film.setGenres(genreDao.getForFilm(film.getId()));
        }
        return films;
    }

    public void addLike(int filmId, int userId) {
        Film film = filmDao.getById(filmId);
        film.setRate(film.getRate() + 1);
        filmDao.update(film);
        likesDao.add(filmId, userId);
    }

    public void removeLike(int filmId, int userId) {
        Film film = filmDao.getById(filmId);
        film.setRate(film.getRate() - 1);
        filmDao.update(film);
        likesDao.remove(filmId, userId);
    }

    private void populateGenres(Film film) {
        List<Genre> genres = film.getGenres().stream().distinct().collect(Collectors.toList());
        film.setGenres(genres);
        Set<Integer> genresId = new HashSet<>();
        for (Genre genre: film.getGenres()) {
            genresId.add(genre.getId());
        }
        genreDao.setForFilm(genresId, film.getId());
    }
}
