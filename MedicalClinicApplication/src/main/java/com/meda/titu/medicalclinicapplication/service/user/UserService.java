package com.meda.titu.medicalclinicapplication.service.user;

import com.meda.titu.medicalclinicapplication.dto.request.user.UserRequest;
import com.meda.titu.medicalclinicapplication.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse save(UserRequest userRequest);

    UserResponse findById(long id);

    UserResponse findByUsername(String username);

    List<UserResponse> findAll();

    List<UserResponse> findAllWithFullNameContaining(String word);

    void deleteById(long id);
}
