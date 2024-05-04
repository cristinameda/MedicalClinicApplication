package com.meda.titu.medicalclinicapplication.service.auth;

import com.meda.titu.medicalclinicapplication.dto.request.auth.LoginRequest;
import com.meda.titu.medicalclinicapplication.dto.response.AuthResponse;

public interface AuthenticationService {
    AuthResponse authenticate(LoginRequest request);
}
