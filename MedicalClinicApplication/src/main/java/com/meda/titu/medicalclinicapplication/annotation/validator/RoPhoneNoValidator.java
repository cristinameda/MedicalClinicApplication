package com.meda.titu.medicalclinicapplication.annotation.validator;

import com.meda.titu.medicalclinicapplication.annotation.RoPhoneNo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RoPhoneNoValidator implements ConstraintValidator<RoPhoneNo, String> {
    @Override
    public boolean isValid(String phoneNo, ConstraintValidatorContext constraintValidatorContext) {
        return phoneNo.matches("^(\\+4|)?(07[0-8][0-9]|02[0-9]{2}|03[0-9]{2})(\\s|\\.|-)?([0-9]{3}(\\s|\\.|-|)){2}$");
    }
}
