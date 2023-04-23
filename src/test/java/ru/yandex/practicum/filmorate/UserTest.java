package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.daoStorge.UserDbStorage;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserTest {

    private final UserDbStorage userStorage;

    private User user = User.builder()
            .id(1)
            .name("Name")
            .login("Login")
            .email("u@mail.com")
            .birthday(LocalDate.parse("1925-03-25"))
            .friends(new HashSet<>(List.of((3))))
            .build();

    private User user1 = User.builder()
            .id(2)
            .name("Nameq")
            .login("Loginq")
            .email("u@mailq.com")
            .birthday(LocalDate.parse("1925-03-20"))
            .build();

    private User user2 = User.builder()
            .id(3)
            .name("Name2")
            .login("Login2")
            .email("u@mail2.com")
            .birthday(LocalDate.parse("1925-04-25"))
            .build();

    @Test
    public void updateTest() {
        userStorage.createUser(user);
        user.setId(12);
        assertEquals(12, user.getId());
    }

    @Test
    public void getAllTest() {
        userStorage.createUser(user);
        userStorage.createUser(user1);
        assertEquals(2, userStorage.getAllUsers().size());
    }

    @Test
    public void getByIdTest() {
        userStorage.createUser(user);
        userStorage.createUser(user1);
        assertEquals(1, userStorage.getUserById(1).getId());
    }

    @Test
    public void createTest() {
        userStorage.createUser(user);
        User userActual = userStorage.getUserById(1);
        assertEquals(user.getId(), userActual.getId());
        assertEquals(user.getEmail(), userActual.getEmail());
        assertEquals(user.getLogin(), userActual.getLogin());
        assertEquals(user.getName(), userActual.getName());
        assertEquals(user.getBirthday(), userActual.getBirthday());
    }

}