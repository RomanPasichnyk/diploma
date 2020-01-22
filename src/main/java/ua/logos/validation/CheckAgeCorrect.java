package ua.logos.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Constraint(validatedBy = CheckAgeCorrectValidator.class)
public @interface CheckAgeCorrect {

    String message() default "Age is incorrect";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
