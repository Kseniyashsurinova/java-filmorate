package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Set;

public interface UserStorage {

    public User createUser(User user);

    public User updateUser(User user);

    public Collection<User> getAllUsers();

    public User getUserById(Integer id);

    public User addFriend(Integer id, Integer friendId);

    public void removeFriend(Integer userId, Integer friendId);

    public Set<User> getCommonFriends(int user1Id, int user2Id);

    public Collection<User> getFriend(Integer id);

}