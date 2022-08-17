package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;
    private final UserService userService;

    @Autowired
    public FilmController(FilmService service, UserService userService) {
        this.filmService = service;
        this.userService = userService;
    }

    @GetMapping
    public List<Film> getFilms() {
        return filmService.getAll();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable int id) {
        return filmService.getById(id);
    }

    @PostMapping
    public Film addFilm(@RequestBody @Valid Film film) {
        return filmService.add(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody @Valid Film film) {
        return filmService.update(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@PathVariable int id, @PathVariable int userId) {
        return filmService.addLike(filmService.getById(id), userService.getById(userId));
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film removeLike(@PathVariable int id, @PathVariable int userId) {
        return filmService.removeLike(filmService.getById(id), userService.getById(userId));
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10") int count) {
        return filmService.getMostPopular(count);
    }
}
