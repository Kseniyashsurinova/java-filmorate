package ru.yandex.practicum.filmorate.exceptions;

public class Errors {

    private String error;

    public Errors(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
