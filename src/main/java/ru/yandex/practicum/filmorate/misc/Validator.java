package ru.yandex.practicum.filmorate.misc;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class Validator {

    public static void validate(Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Кино еще не изобрели!");
        }
    }

    public static void validate(User user) throws ValidationException {
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может содержать пробелы!");
        }
        if ((user.getName() == null) || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
