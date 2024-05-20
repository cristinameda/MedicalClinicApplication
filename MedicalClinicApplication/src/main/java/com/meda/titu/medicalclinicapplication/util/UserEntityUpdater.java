package com.meda.titu.medicalclinicapplication.util;

import com.meda.titu.medicalclinicapplication.dto.request.user.UserRequest;
import com.meda.titu.medicalclinicapplication.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserEntityUpdater {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserEntityUpdater.class);
    private final PasswordEncoder passwordEncoder;

    public <T extends User> void updateUserEntity(T user, UserRequest updatedUser) {
        try {
            for (Field field : updatedUser.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object updatedValue = field.get(updatedUser);
                Object currentValue = field.get(user);

                if (updatedValue != null && !Objects.equals(updatedValue, currentValue)) {
                    field.set(user, updatedValue);
                }
            }
        } catch (IllegalAccessException exception) {
            LOGGER.error(exception.getMessage());
        }
    }

    public <T extends User> void updatePassword(T user, String password) {
        user.setPassword(passwordEncoder.encode(password));
    }
}
