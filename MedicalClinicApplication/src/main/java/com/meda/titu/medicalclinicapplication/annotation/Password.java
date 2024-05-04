package com.meda.titu.medicalclinicapplication.annotation;

import com.meda.titu.medicalclinicapplication.annotation.validator.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {
    String message() default "password must be at least 8 characters long," +
            "contain at least one letter, one digit and one special character (!?-_#$%&@)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
