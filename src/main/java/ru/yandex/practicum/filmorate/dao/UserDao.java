package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserDao extends StandartDao<User> {

    List<User> getMutualFriends(int userId, int friendId);

    List<User> getFriends(int userId);
}
