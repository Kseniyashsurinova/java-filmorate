package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class User {

    @EqualsAndHashCode.Exclude
    private int id;

    @Email(message = "Электронная почта должна содержать @")
    @NotBlank(message = "Почта не может быть пусто")
    private String email;

    @NotNull(message = "Логин не может быть пустым")
    @Pattern(regexp = "\\S+")
    private String login;
    private String name;
    @Past

    @NotNull(message = "birthday не может быть пустым")
    @Past(message = "Дата рождения не должна быть позже настоязего времени")
    private LocalDate birthday;

    protected Set<Integer> friends;

}
