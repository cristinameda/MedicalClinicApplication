package com.meda.titu.medicalclinicapplication.annotation.validator;

import com.meda.titu.medicalclinicapplication.annotation.UniqueUsername;
import com.meda.titu.medicalclinicapplication.repository.user.AdminRepository;
import com.meda.titu.medicalclinicapplication.repository.user.DoctorRepository;
import com.meda.titu.medicalclinicapplication.repository.user.PatientRepository;
import com.meda.titu.medicalclinicapplication.repository.user.RadiologistRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    private final AdminRepository adminRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final RadiologistRepository radiologistRepository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return username != null && !adminRepository.existsByUsername(username) && !doctorRepository.existsByUsername(username) &&
                !patientRepository.existsByUsername(username) && !radiologistRepository.existsByUsername(username);
    }
}
