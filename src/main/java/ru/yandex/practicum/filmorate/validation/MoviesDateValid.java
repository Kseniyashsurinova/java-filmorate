package ru.yandex.practicum.filmorate.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Month;

public class MoviesDateValid implements ConstraintValidator<MoviesDate, LocalDate> {

    private static final LocalDate FIRST_MOVIE_DATE
            = LocalDate.of(1895, Month.DECEMBER, 28);

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        return localDate.isAfter(FIRST_MOVIE_DATE);
    }
}
