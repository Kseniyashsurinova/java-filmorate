package ru.yandex.practicum.filmorate.сontroller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;

    @PostMapping()
    public User createUser(@RequestBody User user) {
        log.debug("Запрос создание нового пользователя");
        return userService.createUser(user);
    }

    @GetMapping()
    public Collection<User> getAllUsers() {
        log.debug("Запрошен список всех пользователей");
        return userService.getAllUsers();
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        log.debug("Запрос обновления пользователя");
        return userService.updateUser(user);
    }

}
