package ru.yandex.practicum.filmorate.storage.daoStorge;

import lombok.Builder;
import ru.yandex.practicum.filmorate.model.Like;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.LikeStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Builder
@Component
public class LikeDbStorage implements LikeStorage {

    private final JdbcTemplate jdbcTemplate;

    public LikeDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addLike(int userId, int filmId) {
        String sql = "MERGE INTO likes key(film_id,user_id) values (?, ?)";
        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public void deleteLikes(int userId, int filmId) {
        String sqlQuery = "DELETE FROM likes WHERE film_id = ? AND user_id = ?";
        jdbcTemplate.update(sqlQuery, filmId, userId);
    }

    @Override
    public List<Like> getLikes(int userId, int filmId) {
        String sqlQuery = "SELECT * FROM LIKES WHERE USER_ID = ? AND FILM_ID = ?";
        return jdbcTemplate.query(sqlQuery, this::mapRowLike, userId, filmId);
    }

    private Like mapRowLike(ResultSet resultSet, int nowNum) throws SQLException {
        return Like.builder()
                .filmId(resultSet.getInt("film_id"))
                .userId(resultSet.getInt("user_id"))
                .build();
    }

}

