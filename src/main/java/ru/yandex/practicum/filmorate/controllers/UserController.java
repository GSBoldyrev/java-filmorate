package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final UserService service;
    private final UserStorage storage;

    @Autowired
    public UserController(UserService service, UserStorage storage) {
        this.service = service;
        this.storage = storage;
    }

    @GetMapping
    public List<User> getUsers() {
        return storage.getUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return storage.getUserById(id);
    }

    @PostMapping
    public User addUser(@RequestBody @Valid User user) {
        return storage.addUser(user);
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User user) {
        return storage.updateUser(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addToFriends(@PathVariable int id, @PathVariable int friendId) {
        return service.addToFriends(storage.getUserById(id), storage.getUserById(friendId));
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User removeFromFriends(@PathVariable int id, @PathVariable int friendId) {
        return service.removeFromFriends(storage.getUserById(id), storage.getUserById(friendId));
    }

    @GetMapping("/{id}/friends")
    public Set<User> getFriends(@PathVariable int id) {
        return service.getFriends(storage.getUserById(id));
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Set<User> getMutualFriends(@PathVariable int id, @PathVariable int otherId) {
        return service.getMutualFriends(storage.getUserById(id), storage.getUserById(otherId));
    }
}
