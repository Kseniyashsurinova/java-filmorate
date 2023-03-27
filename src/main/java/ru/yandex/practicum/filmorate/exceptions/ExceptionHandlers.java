package ru.yandex.practicum.filmorate.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice  ("ru.yandex.practicum.filmorate.сontroller")
public class ExceptionHandlers {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Errors ValidationException(final ValidationException e) {
        return new Errors("Ошибка валидации" + e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Errors notFoundError(final NotFoundException e) {
        return new Errors("404 " + e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Errors exceptions(final Throwable e) {
        return new Errors("Внутренняя ошибка сервера" + e.getMessage());
    }

}
