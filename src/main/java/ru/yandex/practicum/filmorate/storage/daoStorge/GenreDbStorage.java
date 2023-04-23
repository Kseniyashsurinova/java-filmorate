package ru.yandex.practicum.filmorate.storage.daoStorge;

import lombok.Builder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Builder
@Component
public class GenreDbStorage implements GenreStorage {
    private JdbcTemplate jdbcTemplate;

    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<Genre> getAllGenres() {
        return jdbcTemplate.query("select * from genres", (rs, rowNum) -> mapRowToGenre(rs));
    }

    @Override
    public Genre getGenreById(int id) throws ResponseStatusException {
        String sqlQuery = "SELECT genre_id, " +
                "genre_name " +
                "FROM genres AS g " +
                "WHERE genre_id = ?;";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> mapRowToGenre(rs), id)
                .stream()
                .findAny()
                .orElseThrow(() -> new NotFoundException("Жанр с id=" + id + " не существует"));
    }

    private Genre mapRowToGenre(ResultSet rs) throws SQLException {
            return Genre.builder()
                    .id(rs.getInt("genre_id"))
                    .name(rs.getString("genre_name"))
                    .build();
        }

}
