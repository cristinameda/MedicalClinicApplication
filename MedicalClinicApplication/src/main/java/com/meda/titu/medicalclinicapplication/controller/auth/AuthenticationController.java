package com.meda.titu.medicalclinicapplication.controller.auth;

import com.meda.titu.medicalclinicapplication.dto.request.auth.LoginRequest;
import com.meda.titu.medicalclinicapplication.dto.response.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthenticationController {
    @PostMapping(path = "/login")
    ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request);
}
