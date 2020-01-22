package ua.logos.validation;

import org.springframework.beans.factory.annotation.Autowired;
import ua.logos.repository.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckPhoneNumberExistsValidator implements ConstraintValidator<CheckPhoneNumberExists, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (userRepository.findByPhoneNumber(s) == null) {
            return true;
        }
        return false;
    }
}
