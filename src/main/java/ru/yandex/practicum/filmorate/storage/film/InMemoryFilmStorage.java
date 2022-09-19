package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.misc.Validator.validate;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films = new HashMap<>();
    private int id = 1;

    @Override
    public Film add(Film film) {
        validate(film);
        film.setId(generateId());
        films.put(film.getId(), film);
        log.info("Фильм " + film.getName() + " успешно добавлен");
        return film;
    }

    @Override
    public Film update(Film film) {
        validate(film);
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException("Фильм по ID " + film.getId() + " не найден!");
        }
        films.put(film.getId(), film);
        log.info("Фильм " + film.getName() + " успешно обновлен");
        return film;
    }

    @Override
    public Film delete(Film film) {
        return films.remove(film.getId());
    }

    @Override
    public Film getById(int id) {
        if (films.containsKey(id)) {
            log.info("Вывод фильма по ID " + id);
            return films.get(id);
        } else {
            throw new NotFoundException("Фильм по ID " + id + " не найден!");
        }
    }

    @Override
    public List<Film> getAll() {
        log.info("Вывод списка всех фильмов");
        return new ArrayList<>(films.values());
    }

    @Override
    public List<Film> getMostPopular(int count) {
        log.info("Вывод списка наиболее популярных фильмов");
        return films.values().stream()
                .sorted(this::compare)
                .limit(count)
                .collect(Collectors.toList());
    }

    private int generateId() {
        return id++;
    }

    private int compare(Film f0, Film f1) {

        return f1.getRate() - f0.getRate();
    }
}
