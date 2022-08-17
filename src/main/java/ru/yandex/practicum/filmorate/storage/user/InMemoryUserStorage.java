package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.yandex.practicum.filmorate.misc.Validator.validate;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users = new HashMap<>();
    private int id = 1;

    @Override
    public User add(User user) {
        validate(user);
        if ((user.getName() == null) || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(generateId());
        users.put(user.getId(), user);
        log.info("Пользователь " + user.getName() + " успешно добавлен");
        return user;
    }

    @Override
    public User update(User user) {
        validate(user);
        if (!users.containsKey(user.getId())) {
            throw new NotFoundException("Пользователь по ID " + user.getId() + " не найден!");
        }
        if ((user.getName() == null) || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        log.info("Данные пользователя " + user.getName() + " успешно обновлены");
        return user;
    }

    @Override
    public User delete(User user) {
        return users.remove(user.getId());
    }

    @Override
    public User getById(int id) {
        if (users.containsKey(id)) {
            log.info("Вывод пользователя по ID " + id);
            return users.get(id);
        } else {
            throw new NotFoundException("Пользователь по ID " + id + " не найден!");
        }
    }

    @Override
    public List<User> getAll() {
        log.info("Вывод всех пользователей");
        return new ArrayList<>(users.values());
    }

    private int generateId() {
        return id++;
    }
}
