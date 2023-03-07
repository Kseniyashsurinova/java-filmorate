package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.сontroller.UserController;

import java.time.LocalDate;

@SpringBootTest
public class UserTest {
    private User user;
    private User user1;
    private UserController userController;

    @BeforeEach
    void beforeEach() {
        userController = new UserController(new UserService());
        user = User.builder()
                .id(1)
                .email("people@ya.ru")
                .login("Login")
                .name("Name")
                .birthday(LocalDate.of(2000, 10, 11))
                .build();

        user1 = User.builder()
                .id(5)
                .email("mmm@ya.ru")
                .login("mmm")
                .name("New")
                .birthday(LocalDate.of(2000, 5, 11))
                .build();
    }

    @Test
    public void createTest() {
        userController.createUser(user);
        Assertions.assertEquals(1, userController.getAllUsers().size());
    }

    @Test
    public void updateTest() {
        userController.createUser(user);
        user.setId(12);
        userController.updateUser(user);
        Assertions.assertEquals(12, user.getId());
    }

    @Test
    public void getAllTest() {
        userController.createUser(user);
        userController.createUser(user1);
        Assertions.assertEquals(2, userController.getAllUsers().size());
    }

    @Test
    public void emptyEmail() {
        userController.createUser(user);
        user.setEmail(" ");
        Assertions.assertThrows(
                ValidationException.class,
                () -> userController.updateUser(user)
        );
    }

    @Test
    public void emailTest() {
        userController.createUser(user);
        user.setEmail("people");
        Assertions.assertThrows(
                ValidationException.class,
                () -> userController.updateUser(user)
        );
    }

    @Test
    public void emptyLogin() {
        userController.createUser(user);
        user.setLogin("");
        Assertions.assertThrows(
                ValidationException.class,
                () -> userController.updateUser(user)
        );
    }

    @Test
    public void loginWithSpase() {
        userController.createUser(user);
        user.setLogin("  ");
        Assertions.assertThrows(
                ValidationException.class,
                () -> userController.updateUser(user)
        );
    }

}
