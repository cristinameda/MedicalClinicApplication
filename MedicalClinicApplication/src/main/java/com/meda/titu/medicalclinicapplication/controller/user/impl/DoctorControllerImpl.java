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
@RequestMapping(path = "/doctors")
public class DoctorControllerImpl implements UserController {
    private final UserService doctorService;

    public DoctorControllerImpl(@Qualifier("DoctorService") UserService doctorService) {
        this.doctorService = doctorService;
    }

    @Override
    public ResponseEntity<UserResponse> save(UserRequest doctorRequest) {
        return new ResponseEntity<>(
                doctorService.save(doctorRequest),
                HttpStatus.CREATED
        );
    }

    @Override
    public ResponseEntity<UserResponse> findById(long id) {
        return new ResponseEntity<>(
                doctorService.findById(id),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<UserResponse> findByUsername(String username) {
        return new ResponseEntity<>(
                doctorService.findByUsername(username),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<List<UserResponse>> findAll() {
        return new ResponseEntity<>(
                doctorService.findAll(),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<List<UserResponse>> findAllWithFullNameContaining(String word) {
        return new ResponseEntity<>(
                doctorService.findAllWithFullNameContaining(word),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<Void> deleteById(long id) {
        doctorService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
