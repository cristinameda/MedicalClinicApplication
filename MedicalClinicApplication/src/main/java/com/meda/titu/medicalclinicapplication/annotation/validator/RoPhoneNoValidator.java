package com.meda.titu.medicalclinicapplication.annotation.validator;

import com.meda.titu.medicalclinicapplication.annotation.RoPhoneNo;
import com.meda.titu.medicalclinicapplication.repository.user.AdminRepository;
import com.meda.titu.medicalclinicapplication.repository.user.DoctorRepository;
import com.meda.titu.medicalclinicapplication.repository.user.PatientRepository;
import com.meda.titu.medicalclinicapplication.repository.user.RadiologistRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RoPhoneNoValidator implements ConstraintValidator<RoPhoneNo, String> {
    private final AdminRepository adminRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final RadiologistRepository radiologistRepository;

    @Override
    public boolean isValid(String phoneNo, ConstraintValidatorContext constraintValidatorContext) {
        return phoneNo != null && phoneNo.matches("^(\\+4|)?(07[0-8]{1}[0-9]{1}|02[0-9]{2}|03[0-9]{2}){1}?(\\s|\\.|\\-)?([0-9]{3}(\\s|\\.|\\-|)){2}$") && !adminRepository.existsByPhoneNo(phoneNo) &&
                !doctorRepository.existsByPhoneNo(phoneNo) && !patientRepository.existsByPhoneNo(phoneNo) &&
                !radiologistRepository.existsByPhoneNo(phoneNo);
    }
}
