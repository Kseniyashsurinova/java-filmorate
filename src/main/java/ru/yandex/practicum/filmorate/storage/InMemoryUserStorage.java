package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users = new HashMap<>();
    private int newId = 1;

    // Создание нового пользователя
    @Override
    public User createUser(User user) {
        if (!users.containsKey(user.getId())) {
            user.setId(newId);
            users.put(user.getId(), user);
            newId++;
            log.debug("Пользователь с Id " + user.getId() + " успешно создан");
            return user;
        } else {
            throw new NotFoundException("Пользователь с Id " + user.getId() + " уже добавлен");
        }
    }

    // Обновление пользователя
    @Override
    public User updateUser(User user) {
        if (!users.containsKey(user.getId())) {
            throw new NotFoundException("Пользователь с Id " + user.getId() + " не найден");
        }
        users.replace(user.getId(), user);
        log.debug("Пользователь успешно обновдён");
        return user;
    }

    // Возвращает всех пользователей
    @Override
    public Collection<User> getAllUsers() {
        log.debug("Запрошен список всех пользователей");
        return new ArrayList<>(users.values());
    }

    //Получить пользователя по айди
    @Override
    public User getUserById(Integer id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException("пользователя не найден");
        }
        log.debug("Запрошен пользователя по Id");
        return users.get(id);
    }

}
