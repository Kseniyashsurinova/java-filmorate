package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.Collection;

public interface UserStorage {

    public User createUser(User user);

    public User updateUser(User user);

    public Collection<User> getAllUsers();

    public User getUserById(int id);

    public User addFriend(int userId, int friendId);

    public void removeFriend(int userId, int friendId);

    public ArrayList<User> getCommonFriends(int user1Id, int user2Id);

    public Collection<User> getFriends(int Id);

}
