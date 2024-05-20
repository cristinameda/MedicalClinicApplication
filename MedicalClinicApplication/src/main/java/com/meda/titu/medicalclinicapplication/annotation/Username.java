package com.meda.titu.medicalclinicapplication.annotation;

import com.meda.titu.medicalclinicapplication.annotation.validator.UsernameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameValidator.class)
public @interface Username {

    String message() default "username must contain only letters and digits and be at least 4 and at most 20 characters long";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
