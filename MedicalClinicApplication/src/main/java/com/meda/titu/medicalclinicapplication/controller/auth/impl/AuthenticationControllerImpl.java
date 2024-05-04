package com.meda.titu.medicalclinicapplication.controller.auth.impl;

import com.meda.titu.medicalclinicapplication.controller.auth.AuthenticationController;
import com.meda.titu.medicalclinicapplication.dto.request.auth.LoginRequest;
import com.meda.titu.medicalclinicapplication.dto.response.AuthResponse;
import com.meda.titu.medicalclinicapplication.service.auth.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
@AllArgsConstructor
public class AuthenticationControllerImpl implements AuthenticationController {
    private final AuthenticationService authenticationService;

    @Override
    public ResponseEntity<AuthResponse> login(LoginRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
