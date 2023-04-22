package ru.yandex.practicum.filmorate.storage.daoStorge;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.*;
import java.util.Collection;
import java.util.List;

@Component
@Slf4j
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User createUser(User user) {
        String sqlQuery = "insert into users (email, login, user_name, birthday) " +
                "values (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"user_id"});
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getLogin());
            stmt.setString(3, user.getName());
            stmt.setDate(4, Date.valueOf(user.getBirthday()));
            return stmt;
        }, keyHolder);
        return user;
    }

    @Override
    public User updateUser(User user) {
        final String sql = "UPDATE users SET email = ?, login = ?, user_name = ?, birthday = ? WHERE user_id = ?";
        int update = jdbcTemplate.update(sql, user.getEmail(), user.getLogin(), user.getName(),
                Date.valueOf(user.getBirthday()), user.getId());
        if (update == 0) {
            throw new NotFoundException(String.format("Пользователь с идентификатором %d не найден.", user.getId()));
        } else {
            return user;
        }
    }

    @Override
    public Collection<User> getAllUsers() {
        return jdbcTemplate.query("select * from users", (rs, rowNum) -> mapRowUser(rs));
    }

    @Override
    public User getUserById(int id) {
        String sql = "select * from users WHERE user_id = ? ";
        try {
            User user = jdbcTemplate.queryForObject(sql, (ResultSet rs, int rowNum)
                    -> mapRowUser(rs), id);
            return user;
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(String.format("Пользователь с идентификатором %d не найден.", id));
        }
    }

    private User mapRowUser(ResultSet rs) throws SQLException {
        return User.builder()
                .id(rs.getInt("user_id"))
                .email(rs.getString("email"))
                .login(rs.getString("login"))
                .name(rs.getString("user_name"))
                .birthday(rs.getDate("birthday").toLocalDate())
                .build();
    }

    @Override
    public void addFriend(int id, int friendId) {
        String sql = "INSERT INTO friends(user_id, friend_id) " +
                "values (?, ?)";
        jdbcTemplate.update(sql, id, friendId);
    }

    @Override
    public void removeFriend(int id, int friendId) {
        String sql = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sql, id, friendId);
    }

    @Override
    public List<User> getFriend(int id) {
        String sqlQuery = "SELECT * " +
                "FROM users AS u " +
                "INNER JOIN friends AS f ON u.user_id = f.friend_id " +
                "WHERE f.user_id = ?";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> mapRowUser(rs), id);
    }

    @Override
    public List<User> getCommonFriends(int user1Id, int user2Id) {
        String sqlQuery = "SELECT * FROM users AS u " +
                "INNER JOIN friends AS f ON u.user_id = f.friend_id " +
                "WHERE f.user_id = ? AND f.friend_id IN (SELECT friend_id FROM friends WHERE user_id = ?) ";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> mapRowUser(rs), user1Id, user2Id);
    }

}
