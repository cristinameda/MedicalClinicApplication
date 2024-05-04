package com.meda.titu.medicalclinicapplication.controller.ct;

import com.meda.titu.medicalclinicapplication.dto.request.ct.CreateCTInterpretationRequest;
import com.meda.titu.medicalclinicapplication.dto.response.UserResponse;
import com.meda.titu.medicalclinicapplication.dto.response.ct.CTInterpretationResponse;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CTInterpretationController {
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(value = "Save a new CT interpretation.",
            response = UserResponse.class,
            notes = "Return the created CT interpretation.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CT interpretation successfully created!"),
            @ApiResponse(code = 400, message = "Bad request!")
    })
    ResponseEntity<CTInterpretationResponse> save(@Valid @RequestBody CreateCTInterpretationRequest ctInterpretationRequest,
                                                  @RequestPart(name = "CT-scan") MultipartFile ctScan);

    @PutMapping(
            path = "/id/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(value = "Update a CT interpretation.",
            response = UserResponse.class,
            notes = "Return the updated CT interpretation.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CT interpretation successfully updated!"),
            @ApiResponse(code = 400, message = "Bad request!")
    })
    ResponseEntity<CTInterpretationResponse> update(
            @PathVariable long id,
            @Valid @RequestBody CreateCTInterpretationRequest ctInterpretationRequest
    );

    @GetMapping(
            path = "/id/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(value = "Find CT interpretation by id.",
            response = UserResponse.class,
            notes = "Return the CT interpretation with the given id if found.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CT interpretation found!"),
            @ApiResponse(code = 404, message = "CT interpretation not found!")
    })
    ResponseEntity<CTInterpretationResponse> findById(@PathVariable long id);

    @GetMapping(
            path = "/created/radiologist/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(value = "Find all CT interpretations made by a specific radiologist.",
            notes = "Return all the saved CT interpretations made by that radiologist.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CT interpretations returned!")
    })
    ResponseEntity<List<CTInterpretationResponse>> findAllCreatedByRadiologistWithId(@PathVariable long id);

    @GetMapping(
            path = "/updated/radiologist/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(value = "Find all CT interpretations  by a specific radiologist.",
            notes = "Return all the saved CT interpretations made by that radiologist.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CT interpretations returned!")
    })
    ResponseEntity<List<CTInterpretationResponse>> findAllUpdatedByRadiologistWithId(@PathVariable long id);

    @GetMapping(
            path = "/to-diagnose/doctor/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(value = "Find all CT interpretations that need to be diagnosed by a specific doctor.",
            notes = "Return all the saved CT interpretations to be diagnosed by that doctor.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CT interpretations returned!")
    })
    ResponseEntity<List<CTInterpretationResponse>> findAllToDiagnoseByDoctorWithId(@PathVariable long id);

    @GetMapping(
            path = "/diagnosed/patient/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(value = "Find all CT interpretations with diagnosis of a specific patient.",
            notes = "Return all the saved CT interpretations with diagnosis of that patient.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CT interpretations returned!")
    })
    ResponseEntity<List<CTInterpretationResponse>> findAllDiagnosedForPatientWithId(@PathVariable long id);

    @DeleteMapping(path = "/id/{id}")
    @ApiOperation(value = "Delete an existing CT interpretation by id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CT interpretation successfully deleted!"),
            @ApiResponse(code = 404, message = "CT interpretation not found!")
    })
    ResponseEntity<Void> deleteById(@PathVariable long id);
}
