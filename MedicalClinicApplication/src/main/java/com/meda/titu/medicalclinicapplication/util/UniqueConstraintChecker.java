package com.meda.titu.medicalclinicapplication.util;

import com.meda.titu.medicalclinicapplication.repository.user.AdminRepository;
import com.meda.titu.medicalclinicapplication.repository.user.DoctorRepository;
import com.meda.titu.medicalclinicapplication.repository.user.PatientRepository;
import com.meda.titu.medicalclinicapplication.repository.user.RadiologistRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UniqueConstraintChecker {

    private final AdminRepository adminRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final RadiologistRepository radiologistRepository;

    public boolean isUsernameUnique(String username) {
        return !adminRepository.existsByUsername(username) && !doctorRepository.existsByUsername(username) &&
                !patientRepository.existsByUsername(username) && !radiologistRepository.existsByUsername(username);
    }

    public boolean isEmailUnique(String email) {
        return !adminRepository.existsByEmail(email) && !doctorRepository.existsByEmail(email) &&
                !patientRepository.existsByEmail(email) && !radiologistRepository.existsByEmail(email);
    }

    public boolean isPhoneNoUnique(String phoneNo) {
        return !adminRepository.existsByPhoneNo(phoneNo) && !doctorRepository.existsByPhoneNo(phoneNo) &&
                !patientRepository.existsByPhoneNo(phoneNo) && !radiologistRepository.existsByPhoneNo(phoneNo);
    }
}
