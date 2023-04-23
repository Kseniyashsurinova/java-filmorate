package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.storage.daoStorge.FilmDbStorage;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmTest {

    private  final FilmDbStorage filmDbStorage;
        Film film = Film.builder()
                .id(1)
                .name("film1")
                .description("film1 description")
                .duration(100)
                .releaseDate(LocalDate.parse("1920-11-01"))
                .genres(new HashSet<>(List.of(new Genre(3,"Мультфильм"))))
                .mpa(new Mpa(1, " G"))
                .build();

        Film film1 = Film.builder()
                .id(2)
                .name("film2")
                .description("film2 description")
                .releaseDate(LocalDate.parse("1988-10-10"))
                .duration(90)
                .genres(new HashSet<>(List.of(new Genre(1, "Комедия"))))
                .mpa(new Mpa(1, "G"))
                .build();

    @Test
    public void createTest() {
        filmDbStorage.addFilm(film);
        Assertions.assertEquals(film, filmDbStorage.getAllFilms());
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
        film.setName("film");
        Assertions.assertEquals("film", film.getName());
    }

    @Test
    public void getFilmById() {
        filmDbStorage.addFilm(film);
        Assertions.assertEquals(1, filmDbStorage.getFilmById(1).getId());
    }
}
