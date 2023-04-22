package ru.yandex.practicum.filmorate.storage.daoStorge;

import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.model.Mpa;
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
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
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
        final String sql = "UPDATE films SET film_name = ?, description = ?, duration = ?," +
                "releaseDate = ?, mpa_id =? WHERE film_id = ?";
        jdbcTemplate.update(sql, film.getId(), film.getName(), film.getDescription(), film.getReleaseDate(),
                film.getDuration(), film.getMpa().getId());
        updateGenre(film);
        return getFilmById(film.getId());
    }

    @Override
    public Collection<Film> getAllFilms() {
        String sql = "select * from films";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs));
    }

    @Override
    public Film getFilmById(int filmId) {
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
       return Film.builder()
               .id(rs.getInt("film_id"))
               .name(rs.getString("film_name"))
               .description(rs.getString("description"))
               .duration(rs.getInt("duration"))
               .releaseDate(rs.getDate("releaseDate").toLocalDate())
               .likes(getFilmsLikes(rs.getInt("film_id")))
               .genres(getFilmGenresById(rs.getInt("film_id")))
               .mpa(getFilMpa(rs.getInt("mpa_id")))
               .build();
   }

    private Set<Genre> getFilmGenresById(int id) {
        final String sql = "SELECT * FROM films_ganre INNER JOIN genres ON genres.genre_id = films_ganre.genre_id"
                + " WHERE film_id = ?";
        return new HashSet<>(jdbcTemplate.query(sql, (rs, getNum) -> Genre.builder().id(rs.getInt("genre_id"))
                .name(rs.getString("genre_name")).build(), id));
    }

    private Mpa getFilMpa(int id) throws SQLException {
        final String sql = "SELECT * FROM films INNER JOIN ratings ON ratings.mpa_id = films.mpa_id"
                + " WHERE film_id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, getNum) -> Mpa.builder().id(rs.getInt("mpa_id")).name(rs.getString("mpa_name")).build(), id);
    }

    private void updateGenre(Film film) {
        String deleteGenres = "DELETE FROM films_ganre WHERE film_id = ?";
        jdbcTemplate.update(deleteGenres, film.getId());
            List<Genre> genresList = new ArrayList<>(film.getGenres());
            String sql = "INSERT INTO films_ganre (film_id, genre_id) VALUES (?, ?)";
            genresList.forEach(x -> jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setInt(1, film.getId());
                    ps.setInt(2, genresList.get(i).getId());
                }
                @Override
                public int getBatchSize() {
                    return genresList.size();
                }
            }));
        }

    private Set<Like> getFilmsLikes(int id) {
        final String sql = "SELECT users.* FROM likes JOIN users ON likes.user_id = users.user_id " +
                "WHERE likes.film_id=?";
        return new HashSet<>(jdbcTemplate.query(sql, (rs, getNum) -> Like.builder()
                .filmId(rs.getInt("film_id"))
                .userId(rs.getInt("user_id"))
                .build(), id));
    }

}
