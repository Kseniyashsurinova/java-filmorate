package ru.yandex.practicum.filmorate.storage.daoStorge;

import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

@Component
@Builder
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film addFilm(Film film) {
        final String sql = "INSERT INTO films (film_id, film_name, description, duration, releaseDate,  mpa_id)"
                + " VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, film.getId(), film.getName(), film.getDescription(), film.getReleaseDate(),
                film.getDuration(), film.getMpa().getId());
        final Set<Genre> filmGenres = film.getGenres();
        if (filmGenres != null) {
            final String genreSql = "INSERT INTO film_genres (film_id, genre_id) VALUES (?, ?)";
            filmGenres.forEach(x -> jdbcTemplate.update(genreSql, film.getId(), x.getId()));
        }
        return getFilmById(film.getId());
    }

    @Override
    public Film updateFilm(Film film) {
        final String sql = "UPDATE films SET film_name = ?, film_name = ?, description = ?, duration = ?," +
                "releaseDate = ?, mpa_id =?";
        jdbcTemplate.update(sql, film.getId(), film.getName(), film.getDescription(), film.getReleaseDate(),
                film.getDuration(), film.getMpa().getId());

        final String deleteGenres = "DELETE FROM films_ganre WHERE film_id = ?";
        jdbcTemplate.update(deleteGenres, film.getId());

        final Set<Genre> filmGenres = film.getGenres();

        if (filmGenres != null) {
            final String genreSql = "INSERT INTO films_ganre (film_id, genre_id) VALUES (?, ?)";
            filmGenres.forEach(x -> jdbcTemplate.update(genreSql, film.getId(), x.getId()));
        }
        return getFilmById(film.getId());
    }

    @Override
    public Collection<Film> getAllFilms() {
        return jdbcTemplate.query("select * from FILMS", (rs, rowNum) -> makeFilm(rs));
    }

    @Override
    public Film getFilmById(int filmId) {
        return jdbcTemplate.query("select * from FILMS where FILM_ID = ?", this::makeFilm, filmId);
    }

    public Film makeFilm(ResultSet rs) throws SQLException {
        int filmId = rs.getInt("film_id");
        String filmName = rs.getString("film_name");
        String description = rs.getString("description");
        int duration = rs.getInt("duration");
        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
        int mpaId = rs.getInt("mpa_id");
        String mpaName = rs.getString("mpa_name");
        String genreId = rs.getString("genre_id");
        String genreName = rs.getString("genre_name");
        String filmLikes = rs.getString("user_id");

        return Film.builder()
                .id(filmId)
                .name(filmName)
                .description(description)
                .releaseDate(releaseDate)
                .duration(duration)
                .mpa(new Mpa(mpaId, mpaName))
                .genres(getGenres(genreId, genreName))
                .likes(getLikes(filmLikes))
                .build();
    }

    public Set<Genre> getGenres(String id, String name) {
        Set<Genre> genres = new TreeSet<>();
        if (id != null && name != null) {
            String[] genreId = id.split(",");
            String[] genreName = name.split(",");
            for (int i = 0; i < genreId.length; i++) {
                genres.add(new Genre(
                        Integer.parseInt(genreId[i]),
                        genreName[i]));
            }
        }
        return genres;
    }

    public Set<Integer> getLikes(String likes) {
        Set<Integer> getLikes = new TreeSet<>();
        if (likes != null) {
            String[] filmLikesList = likes.split(",");
            for (String filmLike : filmLikesList) {
                getLikes.add(Integer.parseInt(filmLike));
            }
        }
        return getLikes;
    }

}
