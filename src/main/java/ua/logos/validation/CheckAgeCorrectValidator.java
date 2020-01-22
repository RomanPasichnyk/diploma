package ua.logos.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckAgeCorrectValidator implements ConstraintValidator<CheckAgeCorrect, Integer> {

    @Override
    public boolean isValid(Integer i, ConstraintValidatorContext constraintValidatorContext) {
        if((i > 0) && (i < 99)) {
            return true;
        }
        return false;
    }
}
