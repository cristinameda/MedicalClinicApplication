package com.meda.titu.medicalclinicapplication.annotation;

import com.meda.titu.medicalclinicapplication.annotation.validator.RoPhoneNoValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RoPhoneNoValidator.class)
public @interface RoPhoneNo {
    String message() default "phone number must be unique and romanian";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
