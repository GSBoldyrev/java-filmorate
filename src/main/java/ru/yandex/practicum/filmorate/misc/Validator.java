package ru.yandex.practicum.filmorate.misc;

import ru.yandex.practicum.filmorate.exception.BadRequestException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class Validator {

    public static void validate(Film film) throws BadRequestException {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new BadRequestException("Кино еще не изобрели!");
        }
    }

    public static void validate(User user) throws BadRequestException {
        if (user.getLogin().contains(" ")) {
            throw new BadRequestException("Логин не может содержать пробелы!");
        }
    }
}
