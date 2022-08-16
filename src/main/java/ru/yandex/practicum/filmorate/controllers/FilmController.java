package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {

    private final FilmService service;
    private final FilmStorage storage;
    private final UserStorage userStorage;

    @Autowired
    public FilmController(FilmService service, FilmStorage storage, UserStorage userStorage) {
        this.service = service;
        this.storage = storage;
        this.userStorage = userStorage;
    }

    @GetMapping
    public List<Film> getFilms() {
        return storage.getFilms();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable int id) {
        return storage.getFilmById(id);
    }

    @PostMapping
    public Film addFilm(@RequestBody @Valid Film film) {
        return storage.addFilm(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody @Valid Film film) {
        return storage.updateFilm(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@PathVariable int id, @PathVariable int userId) {
        return service.addLike(storage.getFilmById(id), userStorage.getUserById(userId));
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film removeLike(@PathVariable int id, @PathVariable int userId) {
        return service.removeLike(storage.getFilmById(id), userStorage.getUserById(userId));
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10") int count) {
        return storage.getMostPopularFilms(count);
    }
}
