package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {
    private final FilmStorage FilmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.FilmStorage = filmStorage;
    }

    public Film addFilm(Film film) {
        return FilmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        return FilmStorage.updateFilm(film);
    }

    public Collection<Film> getAllFilms() {
        return FilmStorage.getAllFilms();
    }

    public Film getFilmById(int filmId) {
        return FilmStorage.getFilmById(filmId);
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

    //Топ популярных фильмов
    public Collection<Film> topFilms(int amount) {
        return FilmStorage.getAllFilms().stream()
                .sorted((o1, o2) -> o2.getLikes().size() - o1.getLikes().size())
                .limit(amount)
                .collect(Collectors.toList());
    }

}