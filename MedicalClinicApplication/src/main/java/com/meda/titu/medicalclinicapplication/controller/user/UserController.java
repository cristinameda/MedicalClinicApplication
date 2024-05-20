package com.meda.titu.medicalclinicapplication.controller.user;

import com.meda.titu.medicalclinicapplication.annotation.group.CreateEntityGroup;
import com.meda.titu.medicalclinicapplication.annotation.group.UpdateEntityGroup;
import com.meda.titu.medicalclinicapplication.dto.request.user.UserRequest;
import com.meda.titu.medicalclinicapplication.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

public interface UserController {

    @Operation(summary = "Save a new user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User successfully created!", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad request!")
    })
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<UserResponse> save(@Validated(value = CreateEntityGroup.class) @RequestBody UserRequest userRequest);

    @Operation(summary = "Update an existing user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully updated!", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad request!"),
            @ApiResponse(responseCode = "404", description = "User not found!")
    })
    @PutMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<UserResponse> update(@PathVariable long id, @Validated(value = UpdateEntityGroup.class) @RequestBody UserRequest userRequest);

    @Operation(summary = "Find user by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found!"),
            @ApiResponse(responseCode = "400", description = "Invalid user id supplied!"),
            @ApiResponse(responseCode = "404", description = "User not found!")
    })
    @GetMapping(path = "/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<UserResponse> findById(@PathVariable long id);

    @Operation(summary = "Find user by username.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found!"),
            @ApiResponse(responseCode = "400", description = "Invalid user username supplied!"), //TODO: catch constraint exception and return code 400 with this message; same in interpretation controller
            @ApiResponse(responseCode = "404", description = "User not found!")
    })
    @GetMapping(path = "/username/{username}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<UserResponse> findByUsername(@PathVariable String username);

    @Operation(summary = "Find all users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users returned!")
    })
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<UserResponse>> findAll();

    @Operation(summary = "Find all users with their full name containing a specific word.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users returned!"),
            @ApiResponse(responseCode = "400", description = "Invalid word supplied!")
    })
    @GetMapping(path = "/fullName/{word}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<UserResponse>> findAllWithFullNameContaining(@PathVariable String word);

    @Operation(summary = "Delete an existing user by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully deleted!"),
            @ApiResponse(responseCode = "400", description = "Invalid user id supplied!"),
            @ApiResponse(responseCode = "404", description = "User not found!")
    })
    @DeleteMapping(path = "/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Void> deleteById(@PathVariable long id);
}
