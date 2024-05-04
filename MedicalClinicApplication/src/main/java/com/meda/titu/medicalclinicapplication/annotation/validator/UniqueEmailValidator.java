package com.meda.titu.medicalclinicapplication.annotation.validator;

import com.meda.titu.medicalclinicapplication.annotation.UniqueEmail;
import com.meda.titu.medicalclinicapplication.repository.user.AdminRepository;
import com.meda.titu.medicalclinicapplication.repository.user.DoctorRepository;
import com.meda.titu.medicalclinicapplication.repository.user.PatientRepository;
import com.meda.titu.medicalclinicapplication.repository.user.RadiologistRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    private final AdminRepository adminRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final RadiologistRepository radiologistRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return email != null && !adminRepository.existsByEmail(email) && !doctorRepository.existsByEmail(email) &&
                !patientRepository.existsByEmail(email) && !radiologistRepository.existsByEmail(email);
    }
}
