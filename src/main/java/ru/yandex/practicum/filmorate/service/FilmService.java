package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
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
        checkFilmValidation(film);
        if (films.containsKey(film.getId())) {
            throw new ValidationException("Фильм уже добавлен");
        }
        film.setId(newFilmId);
        films.put(film.getId(), film);
        newFilmId++;
        log.debug("Фильм добавлен");
        return film;
    }

    // Обновление нового фильма
    public Film updateFilm(Film film) {
        checkFilmValidation(film);
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

    // Проверка
    private void checkFilmValidation(Film film) {
        if (film.getName().isEmpty() || film.getName().isBlank()) {
            log.info("Название фильма пустое");
            throw new ValidationException("Название не может быть пустым");
        } else if (film.getDescription().length() > 200) {
            log.info("Слишком длинное описание");
            throw new ValidationException("Максимальная длина описания — 200 символов");
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.info("Дата релиза  раньше минимальной");
            throw new ValidationException("Дата релиза раньше 28 декабря 1895 года");
        } else if (film.getDuration() <= 0) {
            log.info("Отрицательная продолжительность ");
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
    }
}
