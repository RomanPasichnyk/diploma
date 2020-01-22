package ua.logos.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class CheckPhoneNumberCorrectValidator implements ConstraintValidator<CheckPhoneNumberCorrect, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s.matches("((\\+38)|(38))?0\\d{9}")) {
            return true;
        }
        return false;
    }
}
