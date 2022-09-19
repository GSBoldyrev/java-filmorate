package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class GenreDaoImplTest {

    private final GenreDao genre;

    @Test
    void getById() {
        Genre comedy = genre.getById(1);
        Genre cartoon = genre.getById(3);
        Genre bio = genre.getById(6);

        assertEquals("Комедия", comedy.getName());
        assertEquals("Мультфильм", cartoon.getName());
        assertEquals("Биография", bio.getName());
    }

    @Test
    void getByWrongId() {
        NotFoundException e = assertThrows(NotFoundException.class, () -> genre.getById(12));

        assertEquals("Жанр по ID 12 не найден!", e.getMessage());
    }

    @Test
    void getAll() {
        List<Genre> testList = genre.getAll();

        assertEquals(6, testList.size());
        assertEquals("Драма", testList.get(1).getName());
        assertEquals("Боевик", testList.get(3).getName());
        assertEquals("Фантастика", testList.get(4).getName());
    }

    @Test
    void getForFilm() {
        List<Genre> avatar = genre.getForFilm(2);
        List<Genre> batman = genre.getForFilm(4);

        assertEquals(3, avatar.size());
        assertEquals(1, batman.size());
    }

    @Test
    void getForFilmWithWrongId() {
        List<Genre> testList = genre.getForFilm(-1);

        assertTrue(testList.isEmpty());
    }

    @Test
    void setForFilm() {
        // Arrange
        Set<Integer> genres = new HashSet<>();
        genres.add(3);
        // Act
        genre.setForFilm(genres, 3);
        List<Genre> starTrack = genre.getForFilm(4);
        // Assert
        assertEquals(1, starTrack.size());
    }
}