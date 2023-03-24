package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;

public interface FilmStorage {

    public Film addFilm(Film film);

    public Film updateFilm(Film film);

    public Collection<Film> getAllFilms();

    public Film getFilmById(int filmId);

    public void addLikes(int filmId, int like);

    public void removeLikes(int filmId, int like);

    public List<Film> topFilms(int amount);

}
