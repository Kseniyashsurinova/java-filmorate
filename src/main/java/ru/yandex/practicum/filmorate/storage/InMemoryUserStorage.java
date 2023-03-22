package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users = new HashMap<>();
    private int newId = 1;

    // Создание нового пользователя
    @Override
    public User createUser(User user) {
        user.setId(newId);
        users.put(user.getId(), user);
        newId++;
        log.debug("Пользователь с Id " + user.getId() + " успешно создан");
        return user;
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

    // Добавить список друзей
    @Override
    public User addFriend(int id, int friendId) {
        if (!users.containsKey(id) || !users.containsKey(friendId)) {
            throw new NotFoundException("пользователя не найден");
        } else if (!users.containsKey(id) && !users.containsKey(friendId)) {
            throw new NotFoundException("пользователя не найден");
        } else {
            User user = getUserById(id);
            User friend = getUserById(friendId);
            user.addFriends(friendId);
            log.debug("Добавлен новый друг пользователю");
            updateUser(user);
            friend.addFriends(id);
            log.debug("Другу добавляется пользователь");
            updateUser(friend);
            return user;
        }
    }

    @Override
    public void removeFriend(int id, int friendId) {
        if (!users.containsKey(id)) {
            throw new NotFoundException("пользователя не найден");
        }
        User user = getUserById(id);
        User friend = getUserById(friendId);
        user.getFriends().remove(friendId);
        log.debug("Друг удалён у пользователя");
        friend.getFriends().remove(id);
        log.debug("Пользователь удален у друга");
    }

    @Override
    public Collection<User> getFriends(int id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException("пользователя не найден");
        }
        ArrayList<User> friendList = new ArrayList<>();
        User user1 = getUserById(id);
        for (int users : user1.getFriends()) {
            friendList.add(getUserById(users));
        }
        return friendList;
    }

    @Override
    public ArrayList<User> getCommonFriends(int user1Id, int user2Id) {
        ArrayList<User> commonFriendsList = new ArrayList<>();
        User user1 = getUserById(user1Id);
        User user2 = getUserById(user2Id);
        for (int userNum1 : user1.getFriends()) {
            for (int userNum2 : user2.getFriends()) {
                if (userNum1 == userNum2) {
                    commonFriendsList.add(getUserById(userNum2));
                }
            }
        }
        return commonFriendsList;
    }

}
