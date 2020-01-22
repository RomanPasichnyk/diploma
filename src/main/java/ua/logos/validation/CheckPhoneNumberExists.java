package ua.logos.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Constraint(validatedBy = CheckPhoneNumberExistsValidator.class)
public @interface CheckPhoneNumberExists {

    String message() default "Phone number is already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
