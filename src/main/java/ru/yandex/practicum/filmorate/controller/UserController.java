package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService service) {
        this.userService = service;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getById(id);
    }

    @PostMapping
    public User addUser(@RequestBody @Valid User user) {
        return userService.add(user);
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User user) {
        return userService.update(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addToFriends(@PathVariable int id, @PathVariable int friendId) {
        return userService.addFriend(userService.getById(id), userService.getById(friendId));
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User removeFromFriends(@PathVariable int id, @PathVariable int friendId) {
        return userService.removeFriend(userService.getById(id), userService.getById(friendId));
    }

    @GetMapping("/{id}/friends")
    public Set<User> getFriends(@PathVariable int id) {
        return userService.getFriends(userService.getById(id));
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Set<User> getMutualFriends(@PathVariable int id, @PathVariable int otherId) {
        return userService.getMutualFriends(userService.getById(id), userService.getById(otherId));
    }
}
