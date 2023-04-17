package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.daoStorge.FilmDbStorage;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmTest {

    private  final FilmDbStorage filmDbStorage;
        Film film = Film.builder()
                .id(1)
                .name("film1")
                .description("film1 description")
                .releaseDate(LocalDate.parse("1920-11-01"))
                .duration(100)
                .build();

        Film film1 = Film.builder()
                .id(2)
                .name("film2")
                .description("film2 description")
                .releaseDate(LocalDate.parse("1988-10-10"))
                .duration(90)
                .build();

    @Test
    public void createTest() {
        filmDbStorage.addFilm(film);
        Assertions.assertEquals(1, filmDbStorage.getAllFilms().size());
    }

    @Test
    public void getAllTest() {
        filmDbStorage.addFilm(film);
        filmDbStorage.addFilm(film1);
        Assertions.assertEquals(2, filmDbStorage.getAllFilms().size());
    }

    @Test
    public void updateTest() {
        filmDbStorage.addFilm(film);
        film.setId(12);
        Assertions.assertEquals(12, film.getId());
    }

    @Test
    public void getFilmById() {
        filmDbStorage.addFilm(film);
        Assertions.assertEquals(film, filmDbStorage.getFilmById(1));
    }
}
