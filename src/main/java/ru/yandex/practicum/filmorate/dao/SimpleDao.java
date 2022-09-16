package ru.yandex.practicum.filmorate.dao;

import java.util.List;

public interface SimpleDao<T> {

    T getById(int id);

    List<T> getAll();
}
