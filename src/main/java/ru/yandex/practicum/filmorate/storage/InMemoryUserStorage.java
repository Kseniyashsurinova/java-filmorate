package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users = new HashMap<>();
    private int newId = 1;

    // Создание нового пользователя
    @Override
    public User createUser(User user) throws ValidationException {
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
    public User updateUser(User user) throws ValidationException {
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

    // Добавить список друзей
    @Override
    public User addFriend(Integer id, Integer friendId) throws NotFoundException {
        if (!users.containsKey(id)) {
            throw new NotFoundException("Один из пользователей не найден");
        } else if (!users.containsKey(friendId)) {
            throw new NotFoundException("Один из пользователей не найден");
        } else {
            User user = getUserById(id);
            User friend = getUserById(friendId);
            user.setFriends(Collections.singleton(friendId));
            log.debug("Добавлен новый друг пользователю");
            updateUser(user);
            friend.setFriends(Collections.singleton(id));
            log.debug("Другу добавляется пользователь");
            updateUser(friend);
            return user;
        }
    }

    //удаление друзей
    @Override
    public void removeFriend(Integer id, Integer friendId) {
        if (!users.containsKey(id)) {
            throw new NotFoundException("пользователя не найден");
        } else if (!users.containsKey(friendId)) {
            throw new NotFoundException("пользователя не найден");
        } else {
            User user = getUserById(id);
            User friend = getUserById(friendId);
            getFriend(id).remove(getUserById(friendId));
            updateUser(user);
            log.debug("Друг удалён у пользователя");

            getFriend(friendId).remove(getUserById(id));
            log.debug("Пользователь удален у друга");
            updateUser(friend);
        }
    }

    // вызов друга по айди
    @Override
    public Set<User> getFriend(Integer id) {
        Set<User> friendList = new HashSet<>();
        User user1 = getUserById(id);
        if (!users.containsKey(id)) {
            throw new NotFoundException("пользователя не найден");
        }
        for (int users : user1.getFriends()) {
            friendList.add(getUserById(users));
        }
        return friendList;
    }

    // сптсок оьщих друзей
    @Override
    public Set<User> getCommonFriends(int user1Id, int user2Id) {
        Set<Integer> friendsList = new HashSet<>(Set.copyOf(getUserById(user1Id).getFriends()));
        friendsList.retainAll(getUserById(user2Id).getFriends());
        Set<User> commonFriendsList = new HashSet<>();
        User user1 = getUserById(user1Id);
        if (user1 == null) {
            throw new NotFoundException("пользователя не найден");
        }
        User user2 = getUserById(user2Id);
        if (user2 == null) {
            throw new NotFoundException("пользователя не найден");
        }
        for (Integer id : friendsList) {
            commonFriendsList.add(getUserById(id));
        }
        log.info("Вывод общих друзей");

        return commonFriendsList;
    }
}
