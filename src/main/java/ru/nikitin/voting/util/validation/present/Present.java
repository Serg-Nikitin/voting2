package ru.nikitin.voting.util.validation.present;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = DateValidator.class)
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface Present {
    String message() default "{must be the current date}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
