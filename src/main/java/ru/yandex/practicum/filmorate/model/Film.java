package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {

    private int id;
    @NotBlank
    private final String name;
    @Size(max = 200)
    private final String description;
    private final LocalDate releaseDate;
    @Positive
    private final int duration;

    private Set<Integer> usersLiked = new HashSet<>();

    private int likes = 0;

    public void setId(int id) {
        this.id = id;
    }

    public Set<Integer> getUsersLiked() {
        return usersLiked;
    }

    public void setUsersLiked(Set<Integer> usersLiked) {
        this.usersLiked = usersLiked;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
