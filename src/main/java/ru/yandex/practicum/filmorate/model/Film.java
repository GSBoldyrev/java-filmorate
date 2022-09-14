package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    @NonNull
    private final Mpa mpa;
    private int rate;
    private List<Genre> genres = new ArrayList<>();
    private Set<Integer> usersLiked = new HashSet<>();

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Integer> getUsersLiked() {
        return usersLiked;
    }

    public void setUsersLiked(Set<Integer> usersLiked) {
        this.usersLiked = usersLiked;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
