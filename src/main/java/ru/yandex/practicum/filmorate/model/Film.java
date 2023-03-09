package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import ru.yandex.practicum.filmorate.validation.MoviesDate;

import java.time.LocalDate;

@Slf4j
@Data
@Builder
public class Film {

    @EqualsAndHashCode.Exclude
    private int id;

    @NotNull("Название не может быть пустым")
    @NotBlank(message = "Название не может быть пустым")
    private String name;

    @NotNull("Описание не может быть пустым")
    @Size(max = 200, message = "Слишком длинное описание")
    private String description;

    @NotNull("Продолжительность не может быть пустым")
    @Positive(message = "Продолжительность фильма должна быть положительной")
    private Integer duration;

    @NotNull("Дата релиза не может быть пустым")
    @NotBlank(message = "Дата релиза не может быть пустым")
    @MoviesDate(message = "Дата релиза раньше 28 декабря 1895 года")
    private LocalDate releaseDate;
}

