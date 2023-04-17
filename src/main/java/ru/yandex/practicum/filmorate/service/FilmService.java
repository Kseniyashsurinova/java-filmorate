package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {

    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film addFilm(Film film) {
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    public Collection<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public Film getFilmById(int filmId) {
        return filmStorage.getFilmById(filmId);
    }

    // Добавление лайков
    public Film addLikes(int filmId, int userId) {
        Film film = getFilmById(filmId);
        film.getLikes().add(userId);
        log.debug("Добавление лайков");
        updateFilm(film);
        return film;
    }

    // Удаление лайков
    public void removeLikes(int filmId, int like) {
        if (filmId < 0 || like < 0) {
            throw new NotFoundException("Отрицательное значение");
        }
        Film film = getFilmById(filmId);
        film.deleteLike(like);
        log.debug("Удаление лайков");
        updateFilm(film);
    }

    //Топ популярных фильмов по лайкам
    public Collection<Film> topFilms(int amount) {
        return filmStorage.getAllFilms().stream()
                .sorted((o1, o2) -> o2.getLikes().size() - o1.getLikes().size())
                .limit(amount)
                .collect(Collectors.toList());
    }

}