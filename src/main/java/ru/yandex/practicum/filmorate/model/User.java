package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Slf4j
@Data
@Builder
public class User {

    @EqualsAndHashCode.Exclude
    private int id;

    @Email(message = "Электронная почта должна содержать @")
    @NotNull(message = "Почта не может быть пусто")
    @NotBlank(message = "Почта не может быть пусто")
    private String email;

    @NotNull(message = "Логин не может быть пустым")
    @NotBlank(message = "Логин не может быть пустым")
    private String login;

    @NotNull(message = "birthday не может быть пустым")
    @NotBlank(message = "birthday не может быть пустым")
    @Past(message = "Дата рождения не должна быть позже настоязего времени")
    private LocalDate birthday;

    @NotNull(message = "Логин не может быть пустым")
    @NotBlank(message = "Имя не может содержать пробелы")
    private String name;
}
