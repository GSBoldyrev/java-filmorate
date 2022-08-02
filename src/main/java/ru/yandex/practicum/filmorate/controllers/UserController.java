package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.yandex.practicum.filmorate.misc.Validator.validate;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final Map<Integer, User> users = new HashMap<>();
    private int id = 1;

    @GetMapping
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User addUser(@RequestBody @Valid User user) throws ValidationException {
        validate(user);
        user.setId(generateId());
        users.put(user.getId(), user);
        log.info("Пользователь добавлен");
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User user) throws ValidationException {
        validate(user);
        if (!users.containsKey(user.getId())) {
            throw new ValidationException("Пользователь не найден!");
        }
        users.put(user.getId(), user);
        log.info("Данные пользователя обновлены");
        return user;
    }

    private int generateId() {
        return id++;
    }
}
