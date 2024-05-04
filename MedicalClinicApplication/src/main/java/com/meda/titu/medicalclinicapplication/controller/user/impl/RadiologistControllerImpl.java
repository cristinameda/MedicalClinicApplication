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
@RequestMapping(path = "/radiologists")
public class RadiologistControllerImpl implements UserController {
    private final UserService radiologistService;

    public RadiologistControllerImpl(@Qualifier("RadiologistService") UserService radiologistService) {
        this.radiologistService = radiologistService;
    }

    @Override
    public ResponseEntity<UserResponse> save(UserRequest radiologistRequest) {
        return new ResponseEntity<>(
                radiologistService.save(radiologistRequest),
                HttpStatus.CREATED
        );
    }

    @Override
    public ResponseEntity<UserResponse> findById(long id) {
        return new ResponseEntity<>(
                radiologistService.findById(id),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<UserResponse> findByUsername(String username) {
        return new ResponseEntity<>(
                radiologistService.findByUsername(username),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<List<UserResponse>> findAll() {
        return new ResponseEntity<>(
                radiologistService.findAll(),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<List<UserResponse>> findAllWithFullNameContaining(String word) {
        return new ResponseEntity<>(
                radiologistService.findAllWithFullNameContaining(word),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<Void> deleteById(long id) {
        radiologistService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
