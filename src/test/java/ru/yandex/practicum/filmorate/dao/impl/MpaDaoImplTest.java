package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.dao.SimpleDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class MpaDaoImplTest {

    private final SimpleDao<Mpa> mpa;

    @Test
    void getById() {
        Mpa g = mpa.getById(1);
        Mpa pg = mpa.getById(3);
        Mpa nc = mpa.getById(5);

        assertEquals("G", g.getName());
        assertEquals("PG-13", pg.getName());
        assertEquals("NC-17", nc.getName());
    }

    @Test
    void getByWrongId() {
        NotFoundException e = assertThrows(NotFoundException.class, () -> mpa.getById(7));

        assertEquals("Рейтинг по ID 7 не найден!", e.getMessage());
    }

    @Test
    void getAll() {
        List<Mpa> testList = mpa.getAll();

        assertEquals(5, testList.size());
        assertEquals("PG", testList.get(1).getName());
        assertEquals("R", testList.get(3).getName());
    }
}