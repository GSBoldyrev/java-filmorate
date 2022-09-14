package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserDao {

    User add(User user);

    User update(User user);


    int delete(int id);

    User getById(int id);

    List<User> getAll();

    int addFriend(int userId, int friendId);

    int removeFriend(int userId, int friendId);

    List<User> getMutualFriends(int userId, int friendId);

    List<User> getFriends(int userId);
}
