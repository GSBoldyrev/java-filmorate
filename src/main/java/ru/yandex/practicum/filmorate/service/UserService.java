package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.ManyToManyDao;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

import static ru.yandex.practicum.filmorate.misc.Validator.validate;

@Service
@Slf4j
public class UserService {

    private final UserDao users;
    private final ManyToManyDao friends;

    @Autowired
    public UserService(UserDao users, @Qualifier("friendsDaoImpl") ManyToManyDao friends) {
        this.users = users;
        this.friends = friends;
    }

    public User add(User user) {
        validate(user);
        if ((user.getName() == null) || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return users.add(user);
    }

    public User update(User user) {
        validate(user);
        if ((user.getName() == null) || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return users.update(user);
    }

    public int delete(int id) {
        return users.delete(id);
    }

    public User getById(int id) {
        return users.getById(id);
    }

    public List<User> getAll() {
        return users.getAll();
    }

    public void addFriend(int userId, int friendId) {
        friends.add(userId, friendId);
    }

    public void removeFriend(int userId, int friendId) {
        friends.remove(userId, friendId);
    }

    public List<User> getMutualFriends(int userId, int friendId) {
        return users.getMutualFriends(userId, friendId);
    }

    public List<User> getFriends(int userId) {
        return users.getFriends(userId);
    }
}
