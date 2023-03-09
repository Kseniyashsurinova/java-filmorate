package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class FilmService {
    private final Map<Integer, Film> films = new HashMap<>();
    private int newFilmId = 1;

    // Добавление нового фильма
    public Film addFilm(Film film) {
        film.setId(newFilmId);
        films.put(film.getId(), film);
        newFilmId++;
        log.debug("Фильм добавлен");
        return film;
    }

    // Обновление нового фильма
    public Film updateFilm(Film film) {
        if (!getAllFilms().contains(film)) {
            throw new ValidationException("Фильм не найден");
        }
        films.replace(film.getId(), film);
        log.debug("Фильм успешно обновдён");
        return film;
    }

    // Список всех фильмов
    public Collection<Film> getAllFilms() {
        log.debug("Запрошен список всех фильмов");
        return new ArrayList<>(films.values());
    }

}
