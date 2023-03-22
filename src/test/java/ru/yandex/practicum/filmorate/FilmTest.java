package ru.yandex.practicum.filmorate;


import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.сontroller.FilmController;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmTest {

    private Film film;
    private Film film1;
    private FilmController filmController;

    @BeforeEach
    void beforeEach() {
        filmController = new FilmController(new FilmService(new InMemoryFilmStorage()));
        film = Film.builder()
                .id(1)
                .name("Name")
                .description("Description")
                .releaseDate(LocalDate.of(2015, 12, 23))
                .duration(60)
                .build();

        film1 = Film.builder()
                .id(2)
                .name("NameNew")
                .description("Descriptions")
                .releaseDate(LocalDate.of(2021, 3, 20))
                .duration(60)
                .build();
    }

    @Test
    public void createTest() {
        filmController.addFilm(film);
        Assertions.assertEquals(1, filmController.getAllFilms().size());
    }

    @Test
    public void getAllTest() {
        filmController.addFilm(film);
        filmController.addFilm(film1);
        Assertions.assertEquals(2, filmController.getAllFilms().size());
    }

    @Test
    public void updateTest() {
        filmController.addFilm(film);
        film.setId(12);
        Assertions.assertEquals(12, film.getId());
    }
}
