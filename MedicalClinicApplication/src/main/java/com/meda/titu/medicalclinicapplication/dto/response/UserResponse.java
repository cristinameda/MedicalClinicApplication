package com.meda.titu.medicalclinicapplication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private long id;
    private String username;
    private String fullName;
    private String email;
    private String phoneNo;
    private String dob;
}
