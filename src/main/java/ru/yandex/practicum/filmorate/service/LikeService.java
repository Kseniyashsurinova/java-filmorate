package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.storage.LikeStorage;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeStorage likeStorage;

    private final UserService userService;

    private final FilmService filmService;

    // Добавление лайков
    public void addLike(int userId, int filmId) {
        filmService.getFilmById(filmId);
        userService.getUserById(userId);
        likeStorage.addLike(userId, filmId);
    }

    // Удаление лайков
    public void removeLikes(int userId, int filmId) {
        filmService.getFilmById(filmId);
        userService.getUserById(userId);
        if (likeStorage.getLikes(userId, filmId) == null) {
            throw new NotFoundException("Like not found");
        }
        likeStorage.deleteLikes(userId, filmId);
    }
}
