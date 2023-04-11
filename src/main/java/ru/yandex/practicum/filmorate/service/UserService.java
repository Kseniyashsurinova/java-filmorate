package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
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
    public User addFriend(Integer id, Integer friendId) { //throws NotFoundException {
        User user = getUserById(id);
        User friend = getUserById(friendId);
        user.getFriends().add(friendId);
        log.debug("Добавлен новый друг пользователю");
        updateUser(user);
        friend.getFriends().add(id);
        log.debug("Другу добавляется пользователь");
        updateUser(friend);
        return user;
    }

    //Удаление друзей по айди
    public void removeFriend(Integer id, Integer friendId) {
        User user = getUserById(id);
        User friend = getUserById(friendId);
        getFriend(id).remove(friend);
        updateUser(user);
        log.debug("Друг удалён у пользователя");
        getFriend(friendId).remove(user);
        log.debug("Пользователь удален у друга");
        updateUser(friend);
    }

    // получения списка друзей
    public Collection<User> getFriend(Integer id) {
        User user = getUserById(id);
        return user.getFriends().stream()
                .map(userStorage::getUserById)
                .collect(Collectors.toList());
    }

    //получение общих друзей
    public Collection<User> getCommonFriends(int user1Id, int user2Id) {
        Set<Integer> friendsList = new HashSet<>(Set.copyOf(getUserById(user1Id).getFriends()));
        friendsList.retainAll(getUserById(user2Id).getFriends());
        Set<User> commonFriendsList = new HashSet<>();
        for (Integer id : friendsList) {
            commonFriendsList.add(getUserById(id));
        }
        log.info("Вывод общих друзей");
        return commonFriendsList;
    }
}