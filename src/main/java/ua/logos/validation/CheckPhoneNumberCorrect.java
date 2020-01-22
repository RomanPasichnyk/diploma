package ua.logos.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Constraint(validatedBy = CheckPhoneNumberCorrectValidator.class)
public @interface CheckPhoneNumberCorrect {

    String message() default "Phone number is incorrect. (Ex: +380123456789)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
