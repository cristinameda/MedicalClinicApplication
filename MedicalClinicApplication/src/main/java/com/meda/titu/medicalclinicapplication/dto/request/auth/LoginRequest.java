package com.meda.titu.medicalclinicapplication.dto.request.auth;

import com.meda.titu.medicalclinicapplication.annotation.Password;
import com.meda.titu.medicalclinicapplication.annotation.UniqueUsername;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @UniqueUsername
    private String username;
    @Password
    private String password;
}
