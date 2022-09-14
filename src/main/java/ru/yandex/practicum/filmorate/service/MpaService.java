package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.MpaDao;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

@Service
public class MpaService {

    private final MpaDao mpa;

    @Autowired
    public MpaService(MpaDao mpa) {
        this.mpa = mpa;
    }

    public Mpa getById(int id) {
        return mpa.getById(id);
    }

    public List<Mpa> getAll() {
        return mpa.getAll();
    }
}
