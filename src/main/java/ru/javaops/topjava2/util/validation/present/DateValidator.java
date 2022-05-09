package ru.javaops.topjava2.util.validation.present;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DateValidator implements ConstraintValidator<Present, LocalDate> {
    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        LocalDate now = LocalDate.now();
        return now.compareTo(value) == 0;
    }
}
