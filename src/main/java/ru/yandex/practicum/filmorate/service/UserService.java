package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
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
        checkUserValidation(user);
        if (getAllUsers().contains(user)) {
            System.out.print("Пользователь уже существует");
        } else {
            user.setId(newId);
            users.put(user.getId(), user);
            newId++;
            log.debug("Пользователь с Id " + user.getId() + " успешно создан");
        }
        return user;
    }

    // Обновление пользователя
    public User updateUser(User user) {
        checkUserValidation(user);
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

    //проверка на правильность заполнения полей
    private void checkUserValidation(User user) {
        if (user.getLogin().isEmpty() || user.getLogin().isBlank()) {
            log.info("Логин не может быть пустым или содержать пробелы");
            throw new ValidationException("Неверный формат логина");
        } else if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        } else if (user.getEmail().isEmpty() || user.getEmail().isBlank()) {
            log.info("Почта не может быть пустой или содержать только пробелы");
            throw new ValidationException("Неверный формат электронной почты");
        } else if (!user.getEmail().contains("@")) {
            log.info("Электронная почта должна содержать @");
            throw new ValidationException("Неверный формат электронной почты");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            log.info("Дата рождения не должна быть позже настоязего времени");
            throw new ValidationException("Неверный формат даты рождения");
        }
    }
}