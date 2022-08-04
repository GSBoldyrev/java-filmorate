package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.BadRequestException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static ru.yandex.practicum.filmorate.misc.Validator.validate;

class ValidatorTest {

    @Test
    public void shouldNotValidateOldFilm() {
        Film veryOldFilm =
                new Film("Name", "Description"
                        , LocalDate.of(1600, 11, 11), 1);

        BadRequestException e = assertThrows(BadRequestException.class, () -> validate(veryOldFilm));
        assertEquals("Кино еще не изобрели!", e.getMessage());
    }

    @Test
    public void shouldValidateFirstFilm() {
        Film firstFilm =
                new Film("Name", "Description"
                        , LocalDate.of(1895, 12, 28), 1);

        assertDoesNotThrow(() -> validate(firstFilm));
    }

    @Test
    public void shouldNotValidateLoginWithSpaces() {
        User userWithSpaceInLogin =
                new User("user@yandex.ru", "user user", LocalDate.of(1989, 6, 12));
        userWithSpaceInLogin.setName("name");

        BadRequestException e = assertThrows(BadRequestException.class, () -> validate(userWithSpaceInLogin));
        assertEquals("Логин не может содержать пробелы!", e.getMessage());
    }
}
