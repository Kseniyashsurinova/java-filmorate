package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@Qualifier("inMemoryUserStorage")
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
    public User getUserById(int id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException("пользователя не найден");
        }
        log.debug("Запрошен пользователя по Id");
        return users.get(id);
    }

    @Override
    public void addFriend(int id, int friendId) {
        User user = getUserById(id);
        User friend = getUserById(friendId);
        user.getFriends().add(friendId);
        log.debug("Добавлен новый друг пользователю");
        updateUser(user);
        friend.getFriends().add(id);
        log.debug("Другу добавляется пользователь");
        updateUser(friend);

    }

    @Override
    public void removeFriend(int id, int friendId) {
        User user = getUserById(id);
        User friend = getUserById(friendId);
        getFriend(id).remove(friend);
        updateUser(user);
        log.debug("Друг удалён у пользователя");
        getFriend(friendId).remove(user);
        log.debug("Пользователь удален у друга");
        updateUser(friend);
    }

    @Override
    public List<User> getFriend(int id) {
        User user = getUserById(id);
        return user.getFriends().stream()
                .map(this::getUserById)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getCommonFriends(int user1Id, int user2Id) {
        Set<Integer> friendsList = new HashSet<>(Set.copyOf(getUserById(user1Id).getFriends()));
        friendsList.retainAll(getUserById(user2Id).getFriends());
        List<User> commonFriendsList = new ArrayList<>();
        for (Integer id : friendsList) {
            commonFriendsList.add(getUserById(id));
        }
        log.info("Вывод общих друзей");
        return commonFriendsList;
    }

}
