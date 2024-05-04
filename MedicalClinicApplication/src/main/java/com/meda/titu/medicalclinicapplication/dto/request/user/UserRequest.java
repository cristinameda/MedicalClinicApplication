package com.meda.titu.medicalclinicapplication.dto.request.user;

import com.meda.titu.medicalclinicapplication.annotation.Password;
import com.meda.titu.medicalclinicapplication.annotation.RoPhoneNo;
import com.meda.titu.medicalclinicapplication.annotation.UniqueEmail;
import com.meda.titu.medicalclinicapplication.annotation.UniqueUsername;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @UniqueUsername
    private String username;
    @Password
    private String password;
    @NotBlank
    private String fullName;
    @UniqueEmail
    private String email;
    @RoPhoneNo
    private String phoneNo;
    @NotBlank
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String dob;
}
