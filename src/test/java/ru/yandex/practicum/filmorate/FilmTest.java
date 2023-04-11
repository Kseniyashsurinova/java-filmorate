package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.controller.FilmController;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmTest {

    private Film film;
    private Film film1;
    private User user;
    private FilmController filmController;

    @BeforeEach
    void beforeEach() {
        filmController = new FilmController(new FilmService(new InMemoryFilmStorage()));
        user = new User(1, "people@ya.ru",
                "Login",  LocalDate.of(2000, 10, 11), "Name");
        film = new Film(1, "Name", "Description",
                68, LocalDate.of(2015, 12, 23));
        film1 = new Film(2, "Name2", "Description2",
                682, LocalDate.of(2019, 12, 23));
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

    @Test
    public void getFilmById() {
        filmController.addFilm(film);
        Assertions.assertEquals(film, filmController.getFilmById(1));
    }

    @Test
    public void addLikes() {
        filmController.addFilm(film);
        filmController.addLikes(user.getId(), 1);
        Assertions.assertEquals(film.getLikes().size(), 1);
    }

}
