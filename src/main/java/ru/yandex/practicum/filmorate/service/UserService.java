package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class UserService {

    private final UserStorage users;

    @Autowired
    public UserService(UserStorage users) {
        this.users = users;
    }

    public User add(User user) {
        return users.add(user);
    }

    public User update(User user) {
        return users.update(user);
    }

    public User delete(User user) {
        return users.delete(user);
    }

    public User getById(int id) {
        return users.getById(id);
    }

    public List<User> getAll() {
        return users.getAll();
    }

    public User addFriend(User user, User newFriend) {
        user.getFriendsId().add(newFriend.getId());
        newFriend.getFriendsId().add(user.getId());
        users.update(user);
        users.update(newFriend);
        log.info("Пользователь " + user.getName() + " добавил в друзья пользователя " + newFriend.getName());
        return user;
    }

    public User removeFriend(User user, User notFriend) {
        user.getFriendsId().remove(notFriend.getId());
        notFriend.getFriendsId().remove(user.getId());
        users.update(user);
        users.update(notFriend);
        log.info("Пользователь " + user.getName() + " удалил из друзей пользователя " + notFriend.getName());
        return user;
    }

    public Set<User> getMutualFriends(User user1, User user2) {
        Set<User> mutualFriends = new HashSet<>();
        for (Integer id : user1.getFriendsId()) {
            mutualFriends.add(users.getById(id));
        }
        for (Integer id : user2.getFriendsId()) {
            mutualFriends.add(users.getById(id));
        }
        mutualFriends.remove(user1);
        mutualFriends.remove(user2);
        log.info("Вывод списка общих друзей пользователей " + user1.getName() + " и " + user2.getName());
        return mutualFriends;
    }

    public Set<User> getFriends(User user) {
        Set<User> friends = new HashSet<>();
        for (Integer id : user.getFriendsId()) {
            friends.add(users.getById(id));
        }
        log.info("Вывод списка друзей пользователя " + user.getName());
        return friends;
    }
}
