package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films = new HashMap<>();
    private int newFilmId = 1;
    private UserStorage userStorage;

    // Добавление нового фильма
    @Override
    public Film addFilm(Film film) throws ValidationException {
        film.setId(newFilmId);
        films.put(film.getId(), film);
        newFilmId++;
        log.debug("Фильм добавлен");
        return film;
    }

    // Обновление нового фильма
    @Override
    public Film updateFilm(Film film) throws NotFoundException {
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

    // Добавление лайков
    @Override
    public void addLikes(int filmId, int userId) {
        Film film = getFilmById(filmId);
        film.setLikes(Collections.singleton(userId));
        log.debug("Добавление лайков");
        updateFilm(film);
    }

    // Удаление лайков
    @Override
    public void removeLikes(int filmId, int like) throws NotFoundException {
        if (filmId < 0 || like < 0) {
            throw new NotFoundException("Отрицательное значение");
        }
        Film film = getFilmById(filmId);
        film.deleteLike(like);
        log.debug("Удаление лайков");
        updateFilm(film);
    }

    //Топ популярных фильмов
    @Override
    public List<Film> topFilms(int amount) {
        return getAllFilms().stream()
                .filter(film -> film.getLikes() != null)
                .sorted(sortPopularFilm())
                .limit(amount)
                .collect(Collectors.toList());
    }

    // метод сортировки
    public Comparator<Film> sortPopularFilm() {
        return Comparator.comparing(film -> film.getLikes().size(), Comparator.reverseOrder());
    }
}
