package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {

    public User createUser(User user);

    public User updateUser(User user);

    public Collection<User> getAllUsers();

    public User getUserById(Integer id);

}