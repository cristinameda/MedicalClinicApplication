package com.meda.titu.medicalclinicapplication.annotation.validator;

import com.meda.titu.medicalclinicapplication.annotation.UniqueEmail;
import com.meda.titu.medicalclinicapplication.util.UniqueConstraintChecker;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    private final UniqueConstraintChecker uniqueConstraintChecker;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return uniqueConstraintChecker.isEmailUnique(email);
    }
}
