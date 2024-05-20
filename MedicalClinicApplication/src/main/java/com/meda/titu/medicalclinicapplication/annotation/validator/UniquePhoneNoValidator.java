package com.meda.titu.medicalclinicapplication.annotation.validator;

import com.meda.titu.medicalclinicapplication.annotation.UniquePhoneNo;
import com.meda.titu.medicalclinicapplication.util.UniqueConstraintChecker;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UniquePhoneNoValidator implements ConstraintValidator<UniquePhoneNo, String> {
    private final UniqueConstraintChecker uniqueConstraintChecker;

    @Override
    public boolean isValid(String phoneNo, ConstraintValidatorContext constraintValidatorContext) {
        return uniqueConstraintChecker.isPhoneNoUnique(phoneNo);
    }
}
