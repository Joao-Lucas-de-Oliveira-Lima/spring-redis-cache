package edu.jl.springrediscache.validation.constraint;

import edu.jl.springrediscache.validation.validator.YearOfReleaseValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = YearOfReleaseValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidYearOfRelease {
    String message() default "Invalid year of release";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
