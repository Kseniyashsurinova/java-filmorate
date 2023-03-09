package ru.yandex.practicum.filmorate.service;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class UserService {

    private final Map<Integer, User> users = new HashMap<>();
    private int newId = 1;

    // Создание нового пользователя
    public User createUser(User user) {
            user.setId(newId);
            users.put(user.getId(), user);
            newId++;
            log.debug("Пользователь с Id " + user.getId() + " успешно создан");
        return user;
    }

    @Valid
    // Обновление пользователя
    public User updateUser(User user) {
        if (!getAllUsers().contains(user)) {
            throw new ValidationException("Пользователь с Id " + user.getId() + " не найден");
        }
        users.replace(user.getId(), user);
        log.debug("Пользователь успешно обновдён");
        return user;
    }

    // Возвращает всех пользователей
    public Collection<User> getAllUsers() {
        log.debug("Запрошен список всех пользователей");
        return new ArrayList<>(users.values());
    }

}