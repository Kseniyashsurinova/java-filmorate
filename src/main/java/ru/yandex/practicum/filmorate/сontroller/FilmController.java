package ru.yandex.practicum.filmorate.сontroller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
@Slf4j

public class FilmController {

    private final FilmService filmService;

    @PostMapping()
    public Film addFilm(@Valid @RequestBody Film film) {
        log.debug("Запрос создание нового пользователя");
        return filmService.addFilm(film);
    }

    @GetMapping()
    public Collection<Film> getAllFilms() {
        log.debug("Запрошен список всех пользователей");
        return filmService.getAllFilms();
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.debug("Запрос обновления пользователя");
        return filmService.updateFilm(film);
    }
}
