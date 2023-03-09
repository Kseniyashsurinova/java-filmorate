package ru.yandex.practicum.filmorate.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class MoviesDateValid implements ConstraintValidator<MoviesDate, LocalDate> {

    @Override
    public void initialize(MoviesDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        LocalDate firstDate = LocalDate.parse("1895-12-28");
        return localDate.isAfter(firstDate);
    }
}
