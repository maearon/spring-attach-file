package com.example.springboilerplate.validation;

import com.example.springboilerplate.dto.RegisterDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, RegisterDto> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(RegisterDto dto, ConstraintValidatorContext context) {
        if (dto == null) {
            return false;
        }

        String password = dto.getPassword();
        String matchingPassword = dto.getMatchingPassword();

        return StringUtils.hasText(password)
                && StringUtils.hasText(matchingPassword)
                && password.equals(matchingPassword);
    }
}
