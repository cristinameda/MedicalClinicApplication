package com.meda.titu.medicalclinicapplication.controller.user.impl;

import com.meda.titu.medicalclinicapplication.controller.user.UserController;
import com.meda.titu.medicalclinicapplication.dto.request.user.UserRequest;
import com.meda.titu.medicalclinicapplication.dto.response.UserResponse;
import com.meda.titu.medicalclinicapplication.service.user.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/patients")
public class PatientControllerImpl implements UserController {
    private final UserService patientService;

    public PatientControllerImpl(@Qualifier("PatientService") UserService patientService) {
        this.patientService = patientService;
    }

    @Override
    public ResponseEntity<UserResponse> save(UserRequest patientRequest) {
        return new ResponseEntity<>(
                patientService.save(patientRequest),
                HttpStatus.CREATED
        );
    }

    @Override
    public ResponseEntity<UserResponse> findById(long id) {
        return new ResponseEntity<>(
                patientService.findById(id),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<UserResponse> findByUsername(String username) {
        return new ResponseEntity<>(
                patientService.findByUsername(username),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<List<UserResponse>> findAll() {
        return new ResponseEntity<>(
                patientService.findAll(),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<List<UserResponse>> findAllWithFullNameContaining(String word) {
        return new ResponseEntity<>(
                patientService.findAllWithFullNameContaining(word),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<Void> deleteById(long id) {
        patientService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
