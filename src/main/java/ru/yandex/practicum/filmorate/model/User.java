package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Slf4j
@Data
@Builder
public class User {

    @EqualsAndHashCode.Exclude
    private int id;

    @Email(message = "Электронная почта должна содержать @")
    @NotNull("Почта не может быть пусто")
    @NotBlank(message = "Почта не может быть пусто")
    private String email;

    @NotNull("Логин не может быть пустым")
    @NotBlank(message = "Логин не может быть пустым")
    private String login;

    @NotNull("birthday не может быть пустым")
    @NotBlank(message = "birthday не может быть пустым")
    @Past(message = "Дата рождения не должна быть позже настоязего времени")
    private LocalDate birthday;

    @NotNull("Логин не может быть пустым")
    @NotBlank(message = "Имя не может содержать пробелы")
    private String name;
}
