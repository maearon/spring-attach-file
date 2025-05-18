package com.example.springboilerplate.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        try {
            Field passwordField = obj.getClass().getDeclaredField("password_digest");
            Field matchingPasswordField = obj.getClass().getDeclaredField("matchingPassword");
            
            passwordField.setAccessible(true);
            matchingPasswordField.setAccessible(true);
            
            String password = (String) passwordField.get(obj);
            String matchingPassword = (String) matchingPasswordField.get(obj);
            
            return StringUtils.hasText(password) && password.equals(matchingPassword);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return false;
        }
    }
}
