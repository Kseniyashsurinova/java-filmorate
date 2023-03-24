package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Slf4j
@Service
public class UserService {
    private final InMemoryUserStorage inMemoryUserStorage;

    @Autowired
    public UserService(InMemoryUserStorage inMemoryUserStorage){
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public User createUser(User user) {
        return inMemoryUserStorage.createUser(user);
    }

    public User updateUser(User user) {
        return inMemoryUserStorage.updateUser(user);
    }

    public Collection<User> getAllUsers() {
        return inMemoryUserStorage.getAllUsers();
    }

    public User getUserById(int id) {
        return inMemoryUserStorage.getUserById(id);
    }

    public User addFriend(int id, int friendId) {
        return inMemoryUserStorage.addFriend(id, friendId);
    }

    public void removeFriend(Integer id, Integer friendId) {
        inMemoryUserStorage.removeFriend(id, friendId);
    }

    public Collection<User> getFriend(int Id) {
        return inMemoryUserStorage.getFriend(Id);
    }

    public Set<User> getCommonFriends(int user1Id, int user2Id) {
        return inMemoryUserStorage.getCommonFriends(user1Id, user2Id);
    }

}