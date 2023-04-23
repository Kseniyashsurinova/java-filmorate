package ru.yandex.practicum.filmorate.storage.daoStorge;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Component
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<Mpa> getAllMpa() {
        return jdbcTemplate.query("select * from ratings", (rs, rowNum) -> mapRowToMpa(rs));
    }

    @Override
    public Mpa getMpaById(int id) {
       String sqlQuery = "select * from ratings WHERE mpa_id = ?";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> mapRowToMpa(rs), id)
                .stream()
                .findAny()
                .orElseThrow(() -> new NotFoundException("Рейтинга с номером =" + id + " нет"));
    }

    private Mpa mapRowToMpa(ResultSet rs) throws SQLException {
        return Mpa.builder().id(rs.getInt("mpa_id")).name(rs.getString("mpa_name")).build();
    }
}
