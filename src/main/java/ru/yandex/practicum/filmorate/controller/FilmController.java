package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping
@Slf4j
public class FilmController {

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    private final FilmService filmService;

    @PostMapping("/films")
    public Film addFilm(@Valid @RequestBody Film film) {
        log.debug("Запрос создание нового пользователя");
        return filmService.addFilm(film);
    }

    @GetMapping("/films")
    public Collection<Film> getAllFilms() {
        log.debug("Запрошен список всех пользователей");
        return filmService.getAllFilms();
    }

    @GetMapping("/films/{id}")
    public Film getFilmById(@PathVariable int id) {
        log.debug("Получен фильм по id");
        return filmService.getFilmById(id);
    }

    @PutMapping("/films")
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.debug("Запрос обновления пользователя");
        return filmService.updateFilm(film);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void addLikes(@PathVariable int id, @PathVariable int userId) {
        log.debug("пользователь ставит лайк фильму");
        filmService.addLikes(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void removeLikes(@PathVariable int id, @PathVariable int userId) {
        log.debug("пользователь удаляет лайк");
        filmService.removeLikes(id, userId);
    }

    @GetMapping("/films/popular{count}")
    public Collection<Film> popularFilms(@RequestParam(defaultValue = "10") int count) {
        log.debug("пользователь удаляет лайк");
        return filmService.topFilms(count);
    }

}
