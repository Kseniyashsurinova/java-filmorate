package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;

@Slf4j
@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(@Qualifier("userDbStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public Collection<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User getUserById(Integer id) {

        return userStorage.getUserById(id);
    }

    // Добавить список друзей
    public void addFriend(int id, int friendId) throws NotFoundException {
        userStorage.getUserById(id);
        userStorage.getUserById(friendId);
        userStorage.addFriend(id, friendId);
        userStorage.addFriend(id, friendId);
        log.info("Пользователь {} теперь друг {}", friendId, id);
    }

    //Удаление друзей по айди
    public void removeFriend(int id, int friendId) {
        userStorage.removeFriend(id, friendId);
    }

    // получения списка друзей
    public List<User> getFriend(int id) {
       return userStorage.getFriend(id);
    }

    //получение общих друзей
    public Collection<User> getCommonFriends(int user1Id, int user2Id) {
        return userStorage.getCommonFriends(user1Id, user2Id);
    }

}