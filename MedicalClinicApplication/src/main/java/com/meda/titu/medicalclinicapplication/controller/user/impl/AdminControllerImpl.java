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
@RequestMapping(path = "/admins")
public class AdminControllerImpl implements UserController {
    private final UserService adminService;

    public AdminControllerImpl(@Qualifier("AdminService") UserService adminService) {
        this.adminService = adminService;
    }

    @Override
    public ResponseEntity<UserResponse> save(UserRequest adminRequest) {
        return new ResponseEntity<>(
                adminService.save(adminRequest),
                HttpStatus.CREATED
        );
    }

    @Override
    public ResponseEntity<UserResponse> update(long id, UserRequest adminRequest) {
        return new ResponseEntity<>(
                adminService.update(id, adminRequest),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<UserResponse> findById(long id) {
        return new ResponseEntity<>(
                adminService.findById(id),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<UserResponse> findByUsername(String username) {
        return new ResponseEntity<>(
                adminService.findByUsername(username),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<List<UserResponse>> findAll() {
        return new ResponseEntity<>(
                adminService.findAll(),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<List<UserResponse>> findAllWithFullNameContaining(String word) {
        return new ResponseEntity<>(
                adminService.findAllWithFullNameContaining(word),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<Void> deleteById(long id) {
        adminService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
