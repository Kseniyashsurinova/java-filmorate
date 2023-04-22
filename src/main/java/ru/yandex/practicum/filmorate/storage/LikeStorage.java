package ru.yandex.practicum.filmorate.storage;


import ru.yandex.practicum.filmorate.model.Like;

import java.util.List;

public interface LikeStorage {

    void addLike(int userId, int filmId);

    void deleteLikes(int userId, int filmId);

    List<Like> getLikes(int userId, int filmId);
}
