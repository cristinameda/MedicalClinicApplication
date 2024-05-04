package com.meda.titu.medicalclinicapplication.controller.user;

import com.meda.titu.medicalclinicapplication.dto.request.user.UserRequest;
import com.meda.titu.medicalclinicapplication.dto.response.UserResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserController {
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(value = "Save a new user.",
            response = UserResponse.class,
            notes = "Return the created user.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User successfully created!"),
            @ApiResponse(code = 400, message = "Bad request!")
    })
    ResponseEntity<UserResponse> save(@Valid @RequestBody UserRequest userRequest);


    @GetMapping(
            path = "/id/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(value = "Find user by id.",
            response = UserResponse.class,
            notes = "Return the user with the given id if found.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User found!"),
            @ApiResponse(code = 404, message = "User not found!")
    })
    ResponseEntity<UserResponse> findById(@PathVariable long id);

    @GetMapping(
            path = "/username/{username}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(value = "Find user by username.",
            response = UserResponse.class,
            notes = "Return the user with the given username if found.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User found!"),
            @ApiResponse(code = 404, message = "User not found!")
    })
    ResponseEntity<UserResponse> findByUsername(@PathVariable String username);

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(value = "Find all users.",
            notes = "Return all the saved users.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Users returned!")
    })
    ResponseEntity<List<UserResponse>> findAll();

    @GetMapping(
            path = "/fullName/{word}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(value = "Find all users with their full name containing a specific word.",
            notes = "Return all the users that verify the constraint.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Users returned!")
    })
    ResponseEntity<List<UserResponse>> findAllWithFullNameContaining(@PathVariable String word);

    @DeleteMapping(path = "/id/{id}")
    @ApiOperation(value = "Delete an existing user by id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User successfully deleted!"),
            @ApiResponse(code = 404, message = "User not found!")
    })
    ResponseEntity<Void> deleteById(@PathVariable long id);
}
