package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;

public interface UserStorage {

    User createUser(User user);

    User updateUser(User user);

    Collection<User> getAllUsers();

    User getUserById(int id);

    void addFriend(int id, int friendId);

    void removeFriend(int id, int friendId);

    List<User> getFriend(int id);

    List<User> getCommonFriends(int user1Id, int user2Id);

}