package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class FilmService {
    private final InMemoryFilmStorage inMemoryFilmStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage inMemoryFilmStorage) {
        this.inMemoryFilmStorage = new InMemoryFilmStorage();
    }

    public Film addFilm(Film film) {
        return inMemoryFilmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        return inMemoryFilmStorage.updateFilm(film);
    }

    public Collection<Film> getAllFilms() {
        return inMemoryFilmStorage.getAllFilms();
    }

    public Film getFilmById(int filmId) {
        return inMemoryFilmStorage.getFilmById(filmId);
    }

    public void addLikes(int filmId, int like) {
        inMemoryFilmStorage.addLikes(filmId, like);
    }

    public void removeLikes(int filmId, int like){
        inMemoryFilmStorage.removeLikes(filmId, like);
    }

    public List<Film> popularFilms(int amount) {
        return inMemoryFilmStorage.topFilms(amount);
    }

}


