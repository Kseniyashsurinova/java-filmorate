package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films = new HashMap<>();
    private int newFilmId = 1;

    // Добавление нового фильма
    @Override
    public Film addFilm(Film film) {
        film.setId(newFilmId);
        films.put(film.getId(), film);
        newFilmId++;
        log.debug("Фильм добавлен");
        return film;
    }

    // Обновление нового фильма
    @Override
    public Film updateFilm(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException("Фильм не найден");
        }
        films.replace(film.getId(), film);
        log.debug("Фильм успешно обновдён");
        return film;
    }

    // Список всех фильмов
    @Override
    public Collection<Film> getAllFilms() {
        log.debug("Запрошен список всех фильмов");
        return new ArrayList<>(films.values());
    }

    //Получить фильмов по айди
    @Override
    public Film getFilmById(int filmId) {
        if (!films.containsKey(filmId)) {
            throw new NotFoundException("фильм не найден");
        }
        log.debug("Запрошен фильм по Id");
        return films.get(filmId);
    }
}
