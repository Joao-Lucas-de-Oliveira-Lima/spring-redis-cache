package edu.jl.springrediscache.validation.validator;

import edu.jl.springrediscache.validation.constraint.ValidYearOfRelease;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class YearOfReleaseValidator implements ConstraintValidator<ValidYearOfRelease, Short> {

    @Override
    public boolean isValid(Short value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) return false;
        short minYear = 1990;
        short maxYear = (short) LocalDate.now().getYear();
        return value >= minYear && value <= maxYear;
    }
}
