package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.validation.MoviesDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Data
public class Film {

    @EqualsAndHashCode.Exclude
    private int id;

    @NotBlank(message = "Название не может быть пустым")
    private String name;

    @NotBlank(message = "Описание не может быть пустым")
    @Size(max = 200, message = "Слишком длинное описание")
    private String description;

    @NotNull(message = "Продолжительность не может быть пустым")
    @Positive(message = "Продолжительность фильма должна быть положительной")
    private Integer duration;

    @NotNull(message = "Дата релиза не может быть пустым")
    @MoviesDate(message = "Дата релиза раньше 28 декабря 1895 года")
    private LocalDate releaseDate;

    private Set<Integer> likes;
    private final Set<Genre> genres;
    private Mpa mpa;

    public void deleteLike(Integer id) {
        likes.remove(id);
    }

    public Film(int id, String name, String description, Integer duration, LocalDate releaseDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.likes = new HashSet<>();
        this.genres = new HashSet<>();
    }

}
