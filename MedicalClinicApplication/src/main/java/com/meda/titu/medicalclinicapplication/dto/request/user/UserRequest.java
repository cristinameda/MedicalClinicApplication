package com.meda.titu.medicalclinicapplication.dto.request.user;

import com.meda.titu.medicalclinicapplication.annotation.Password;
import com.meda.titu.medicalclinicapplication.annotation.RoPhoneNo;
import com.meda.titu.medicalclinicapplication.annotation.UniqueEmail;
import com.meda.titu.medicalclinicapplication.annotation.UniquePhoneNo;
import com.meda.titu.medicalclinicapplication.annotation.UniqueUsername;
import com.meda.titu.medicalclinicapplication.annotation.Username;
import com.meda.titu.medicalclinicapplication.annotation.group.CreateEntityGroup;
import com.meda.titu.medicalclinicapplication.annotation.group.UpdateEntityGroup;
import jakarta.validation.constraints.Email;
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
    @UniqueUsername(groups = {CreateEntityGroup.class, UpdateEntityGroup.class})
    @Username(groups = {CreateEntityGroup.class, UpdateEntityGroup.class})
    @NotBlank(groups = CreateEntityGroup.class)
    private String username;
    @Password(groups = {CreateEntityGroup.class, UpdateEntityGroup.class})
    @NotBlank(groups = CreateEntityGroup.class)
    private String password;
    @NotBlank(groups = CreateEntityGroup.class)
    private String fullName;
    @UniqueEmail(groups = {CreateEntityGroup.class, UpdateEntityGroup.class})
    @Email(groups = {CreateEntityGroup.class, UpdateEntityGroup.class})
    @NotBlank(groups = CreateEntityGroup.class)
    private String email;
    @UniquePhoneNo(groups = {CreateEntityGroup.class, UpdateEntityGroup.class})
    @RoPhoneNo(groups = {CreateEntityGroup.class, UpdateEntityGroup.class})
    @NotBlank(groups = CreateEntityGroup.class)
    private String phoneNo;
    @NotBlank(groups = CreateEntityGroup.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String dob;
}
