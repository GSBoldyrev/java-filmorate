package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.SimpleDao;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

@Service
public class MpaService {

    private final SimpleDao<Mpa> mpa;

    @Autowired
    public MpaService(SimpleDao<Mpa> mpa) {
        this.mpa = mpa;
    }

    public Mpa getById(int id) {
        return mpa.getById(id);
    }

    public List<Mpa> getAll() {
        return mpa.getAll();
    }
}
