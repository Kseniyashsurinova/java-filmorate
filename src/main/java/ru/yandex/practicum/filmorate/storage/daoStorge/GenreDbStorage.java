package ru.yandex.practicum.filmorate.storage.daoStorge;

import lombok.Builder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Builder
@Component
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<Genre> getAllGenres() {
        return jdbcTemplate.query("select * from genres", (rs, rowNum) -> mapRowToGenre(rs));
    }

    @Override
    public Genre getGenreById(int id) {
        return jdbcTemplate.query("select * from genres WHERE id = ?", this::mapRowToGenre, id);
    }

    private Genre mapRowToGenre(ResultSet rs) throws SQLException {
        return Genre.builder().id(rs.getInt("genre_id")).name(rs.getString("genre_name")).build();
    }

}
