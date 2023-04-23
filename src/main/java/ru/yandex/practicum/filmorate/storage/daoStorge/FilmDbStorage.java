package ru.yandex.practicum.filmorate.storage.daoStorge;

import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
        String sqlQuery = "INSERT INTO films (film_name, description, releaseDate, duration, mpa_id) " +
                "VALUES (?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"film_id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, Date.valueOf((film.getReleaseDate())));
            stmt.setInt(4, film.getDuration());
            stmt.setInt(5, film.getMpa().getId());
            return stmt;
        }, keyHolder);
        int idKey = (int) Objects.requireNonNull(keyHolder.getKey()).longValue();
        film.setId(idKey);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        String sql = "UPDATE films " +
                "SET film_name = ?, description = ?, releaseDate = ?, duration = ?, mpa_id = ?"
                + " WHERE film_id = ?";
        int updatedRows = jdbcTemplate.update(sql, film.getName(), film.getDescription(),
                Date.valueOf(film.getReleaseDate()), film.getDuration(), film.getMpa().getId(), film.getId());
        if (updatedRows == 0) {
            throw new NotFoundException(
                    String.format("Фильм с идентификатором %d не найден.", film.getId()));
        }
        updateGenre(film);
        return getFilmById(film.getId());
    }

    @Override
    public Collection<Film> getAllFilms() {
        String sql = "select * from films";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs));
    }

    @Override
    public Film getFilmById(int filmId) throws NotFoundException {
        String sql = "SELECT * FROM films WHERE film_id = ?";
        Film film;
        try {
            film = jdbcTemplate.queryForObject(sql,
                    (ResultSet rs, int rowNum) -> makeFilm(rs),
                    filmId);
            assert film != null;
            return film;
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(String.format("Фильм с идентификатором %d не найден.", filmId));
        }
    }

    private Film makeFilm(ResultSet rs) throws SQLException {
        Film film = Film.builder()
                .id(rs.getInt("film_id"))
                .name(rs.getString("film_name"))
                .description(rs.getString("description"))
                .duration(rs.getInt("duration"))
                .releaseDate(rs.getDate("releaseDate").toLocalDate())
                .genres(new HashSet<>(rs.getInt("film_id")))
                .mpa(getFilMpa(rs.getInt("mpa_id")))
                .build();
        return film;
    }

    private List<Genre> getFilmGenres(int id) throws NotFoundException {
        final String sql = "SELECT *  FROM genres AS g " +
                "JOIN films_ganre AS f ON g.genre_id = f.genre_id " +
                "WHERE f.film_id = ?;";
        return jdbcTemplate.query(sql, (rs, getNum) -> Genre.builder().id(rs.getInt("genre_id"))
                .name(rs.getString("genre_name")).build(), id);
    }

    private Mpa getFilMpa(int id) throws SQLException {
        final String sql = "SELECT * FROM films INNER JOIN ratings ON ratings.mpa_id = films.mpa_id"
                + " WHERE film_id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, getNum) -> Mpa.builder().id(rs.getInt("mpa_id")).name(rs.getString("mpa_name")).build(), id);
    }

    private void updateGenre(Film film) {
        if (film.getGenres() != null) {
            jdbcTemplate.update("delete from films_ganre where film_id = ?", film.getId());
            for (Genre genre : film.getGenres()) {
                jdbcTemplate.update(
                        "insert into films_ganre(film_id, genre_id) " +
                                "values (?, ?)",
                        film.getId(),
                        genre.getId()
                );
            }
            film.setGenres(new TreeSet<>(film.getGenres()));
        }
    }

    private Set<Like> getFilmsLikes(int id) {
        final String sql = "SELECT * FROM users AS u JOIN likes AS l ON l.user_id = u.user_id " +
                "WHERE l.film_id = ?";
        return new HashSet<>(jdbcTemplate.query(sql, (rs, getNum) -> Like.builder()
                .filmId(rs.getInt("film_id"))
                .userId(rs.getInt("user_id"))
                .build(), id));
    }
}
