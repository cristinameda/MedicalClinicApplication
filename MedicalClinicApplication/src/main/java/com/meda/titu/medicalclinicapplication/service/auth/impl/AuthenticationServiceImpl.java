package com.meda.titu.medicalclinicapplication.service.auth.impl;

import com.meda.titu.medicalclinicapplication.dto.request.auth.LoginRequest;
import com.meda.titu.medicalclinicapplication.dto.response.AuthResponse;
import com.meda.titu.medicalclinicapplication.entity.user.Admin;
import com.meda.titu.medicalclinicapplication.entity.user.Doctor;
import com.meda.titu.medicalclinicapplication.entity.user.Patient;
import com.meda.titu.medicalclinicapplication.entity.user.Radiologist;
import com.meda.titu.medicalclinicapplication.exception.NotFoundException;
import com.meda.titu.medicalclinicapplication.repository.user.AdminRepository;
import com.meda.titu.medicalclinicapplication.repository.user.DoctorRepository;
import com.meda.titu.medicalclinicapplication.repository.user.PatientRepository;
import com.meda.titu.medicalclinicapplication.repository.user.RadiologistRepository;
import com.meda.titu.medicalclinicapplication.security.config.JwtService;
import com.meda.titu.medicalclinicapplication.service.auth.AuthenticationService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AdminRepository adminRepository;
    private final DoctorRepository doctorRepository;
    private final RadiologistRepository radiologistRepository;
    private final PatientRepository patientRepository;

    @Override
    public AuthResponse authenticate(LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            password
                    )
            );
        } catch (Exception e) {
            System.out.printf(e.getMessage());
            e.printStackTrace();
        }
        Optional<Admin> optionalAdmin = adminRepository.findByUsername(username);
        Optional<Doctor> optionalDoctor = doctorRepository.findByUsername(username);
        Optional<Radiologist> optionalRadiologist = radiologistRepository.findByUsername(username);
        Optional<Patient> optionalPatient = patientRepository.findByUsername(username);

        String jwtToken = "";
        long id = -1;

        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            id = admin.getId();
            jwtToken = jwtService.generateToken(getMapId(id), admin);
        }
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            id = doctor.getId();
            jwtToken = jwtService.generateToken(getMapId(id), doctor);
        }
        if (optionalRadiologist.isPresent()) {
            Radiologist radiologist = optionalRadiologist.get();
            id = radiologist.getId();
            jwtToken = jwtService.generateToken(getMapId(id), radiologist);
        }
        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            id = patient.getId();
            jwtToken = jwtService.generateToken(getMapId(id), patient);
        }

        if (jwtToken.isEmpty()) {
            throw new NotFoundException("User with username " + request.getUsername() + " could not be found!");
        }

        LOGGER.info("User with id {} has logged in", id);
        return new AuthResponse(jwtToken);
    }

    private Map<String, Object> getMapId(long id) {
        Map<String, Object> mapId = new HashMap<>();
        mapId.put("id", id);
        return mapId;
    }
}
