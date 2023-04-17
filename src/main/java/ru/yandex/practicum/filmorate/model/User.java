package ru.yandex.practicum.filmorate.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.Set;

@Slf4j
@Data
@Builder
public class User {

    @EqualsAndHashCode.Exclude
    private int id;

    @Email(message = "Электронная почта должна содержать @")
    @NotBlank(message = "Почта не может быть пусто")
    private String email;

    @NotNull(message = "Логин не может быть пустым")
    private String login;

    @NotNull(message = "birthday не может быть пустым")
    @Past(message = "Дата рождения не должна быть позже настоязего времени")
    private LocalDate birthday;

    private String name;
    protected Set<Integer> friends;

   /* public User(int id, String email, String login, LocalDate birthday, String name) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.birthday = birthday;
        this.name = getNameOrLogin(name, login);
        this.friends = new HashSet<>();
    }*/

    private String getNameOrLogin(String name, String login) {
        if (name == null || name.isBlank()) {
            log.info("Имя пустое по этому ставится логин");
            return login;
        } else {
            return name;
        }
    }

}
