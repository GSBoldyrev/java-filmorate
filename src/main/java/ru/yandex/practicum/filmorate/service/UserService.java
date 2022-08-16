package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class UserService {

    private final UserStorage users;

    @Autowired
    public UserService(UserStorage users) {
        this.users = users;
    }

    public User addToFriends(User user, User newFriend) {
        user.getFriends().add(newFriend.getId());
        newFriend.getFriends().add(user.getId());
        users.updateUser(user);
        users.updateUser(newFriend);
        log.info("Пользователь " + user.getName() + " добавил в друзья пользователя " + newFriend.getName());
        return user;
    }

    public User removeFromFriends(User user, User notFriend) {
        user.getFriends().remove(notFriend.getId());
        notFriend.getFriends().remove(user.getId());
        users.updateUser(user);
        users.updateUser(notFriend);
        log.info("Пользователь " + user.getName() + " удалил из друзей пользователя " + notFriend.getName());
        return user;
    }

    public Set<User> getMutualFriends(User user1, User user2) {
        Set<User> mutualFriends = new HashSet<>();
        for (Integer id : user1.getFriends()) {
            mutualFriends.add(users.getUserById(id));
        }
        for (Integer id : user2.getFriends()) {
            mutualFriends.add(users.getUserById(id));
        }
        mutualFriends.remove(user1);
        mutualFriends.remove(user2);
        log.info("Вывод списка общих друзей пользователей " + user1.getName() + " и " + user2.getName());
        return mutualFriends;
    }

    public Set<User> getFriends(User user) {
        Set<User> friends = new HashSet<>();
        for (Integer id : user.getFriends()) {
            friends.add(users.getUserById(id));
        }
        log.info("Вывод списка друзей пользователя " + user.getName());
        return friends;
    }
}
