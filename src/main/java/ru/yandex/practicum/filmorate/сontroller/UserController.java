package ru.yandex.practicum.filmorate.сontroller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping//("/users")

public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping ("/users")
    public User createUser(@Valid @RequestBody User user)  {
        log.debug("Запрос создание нового пользователя");
        return userService.createUser(user);
    }

    @GetMapping("/users")
    public Collection<User> getAllUsers()  {
        log.debug("Запрошен список всех пользователей");
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable int id) {
        log.debug("Запрошен список всех пользователей");
        return userService.getUserById(id);
    }

    @PutMapping("/users")
    public User updateUser(@Valid @RequestBody User user) {
        log.debug("Запрос обновления пользователя");
        return userService.updateUser(user);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public User addFriend(@PathVariable int id, @PathVariable int friendId) {
        log.debug("Запрос добавления в друзья");
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable int id, @PathVariable int friendId) {
        log.debug("Запрос удаления из друзей");
        userService.removeFriend(id, friendId);
    }

    @GetMapping("/users/{id}/friends")
    public Collection<User> getFriend(@PathVariable int id) {
        log.debug("Запрос списка всех друзей пользователя");
        return userService.getFriend(id);
    }

    @GetMapping("/users/{id}/friends/common/{friendId}")
    public Set<User> getCommonFriends(@PathVariable int id, @PathVariable int friendId) {
        log.debug("Запрошен список друзей, общих с другим пользователем");
        return userService.getCommonFriends(id, friendId);
    }

}


