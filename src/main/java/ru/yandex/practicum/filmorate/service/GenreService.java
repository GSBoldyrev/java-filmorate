package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@Service
public class GenreService {

    private final GenreDao genres;

    @Autowired
    public GenreService(GenreDao genres) {
        this.genres = genres;
    }

    public Genre getById(int id) {
        return genres.getById(id);
    }

    public List<Genre> getAll() {
        return genres.getAll();
    }
}
