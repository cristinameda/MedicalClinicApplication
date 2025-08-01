package com.meda.titu.medicalclinicapplication.annotation.validator;

import com.meda.titu.medicalclinicapplication.annotation.UniqueUsername;
import com.meda.titu.medicalclinicapplication.util.UniqueConstraintChecker;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    private final UniqueConstraintChecker uniqueConstraintChecker;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return uniqueConstraintChecker.isUsernameUnique(username);
    }
}
