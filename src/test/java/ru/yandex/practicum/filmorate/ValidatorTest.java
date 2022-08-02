package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static ru.yandex.practicum.filmorate.misc.Validator.validate;

class ValidatorTest {

    @Test
    public void shouldNotValidateOldFilm() {
        // Arrange
        Film veryOldFilm =
                new Film("Name", "Description"
                        , LocalDate.of(1600, 11, 11), 1);
        // Act
        // Assert
        ValidationException e = assertThrows(ValidationException.class, () -> validate(veryOldFilm));
        assertEquals("Кино еще не изобрели!", e.getMessage());
    }

    @Test
    public void shouldValidateFirstFilm() {
        // Arrange
        Film firstFilm =
                new Film("Name", "Description"
                        , LocalDate.of(1895, 12, 28), 1);
        // Act
        // Assert
        assertDoesNotThrow(() -> validate(firstFilm));
    }

    @Test
    public void shouldNotValidateLoginWithSpaces() {
        // Arrange
        User userWithSpaceInLogin =
                new User("user@yandex.ru", "user user", LocalDate.of(1989, 6, 12));
        userWithSpaceInLogin.setName("name");
        // Act
        // Assert
        ValidationException e = assertThrows(ValidationException.class, () -> validate(userWithSpaceInLogin));
        assertEquals("Логин не может содержать пробелы!", e.getMessage());
    }

    @Test
    public void shouldUseLoginInsteadOfBlankName() throws ValidationException {
        // Arrange
        User user =
                new User("user@yandex.ru", "user", LocalDate.of(1989, 6, 12));
        user.setName("");
        // Act
        validate(user);
        // Assert
        assertEquals(user.getLogin(), user.getName());
    }

    @Test
    public void shouldUseLoginInsteadOfNullName() throws ValidationException {
        // Arrange
        User user =
                new User("user@yandex.ru", "user", LocalDate.of(1989, 6, 12));
        // Act
        validate(user);
        // Assert
        assertEquals(user.getLogin(), user.getName());
    }
}
