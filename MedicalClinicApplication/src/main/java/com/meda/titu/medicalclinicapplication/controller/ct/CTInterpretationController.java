package com.meda.titu.medicalclinicapplication.controller.ct;

import com.meda.titu.medicalclinicapplication.dto.request.ct.CreateCTInterpretationRequest;
import com.meda.titu.medicalclinicapplication.dto.request.ct.DiagnoseCTInterpretationRequest;
import com.meda.titu.medicalclinicapplication.dto.request.ct.UpdateCTInterpretationRequest;
import com.meda.titu.medicalclinicapplication.dto.response.ct.CTInterpretationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CTInterpretationController {

    @Operation(summary = "Save a new CT interpretation.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CT interpretation successfully created!", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad request!")
    })
    @PostMapping(
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.MULTIPART_FORM_DATA_VALUE
            },
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<CTInterpretationResponse> save(
            @RequestPart("CT-interpretation") CreateCTInterpretationRequest createCTInterpretationRequest,
            @RequestPart("CT-scan") MultipartFile ctScan
    );

    @Operation(summary = "Update an existing CT interpretation.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CT interpretation successfully updated!", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad request!"),
            @ApiResponse(responseCode = "403", description = "User is forbidden to access this resource!"),
            @ApiResponse(responseCode = "404", description = "CT interpretation not found!")
    })

    @PutMapping(path = "/update/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<CTInterpretationResponse> update(
            @PathVariable("id") long ctInterpretationId,
            @Valid @RequestBody UpdateCTInterpretationRequest ctInterpretationRequest
    );

    @Operation(summary = "Add diagnosis to an existing CT interpretation.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CT interpretation successfully updated!", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad request!"),
            @ApiResponse(responseCode = "403", description = "User is forbidden to access this resource!"),
            @ApiResponse(responseCode = "404", description = "CT interpretation not found!")
    })
    @PutMapping(path = "/diagnose/id/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<CTInterpretationResponse> diagnose(
            @PathVariable("id") long ctInterpretationId,
            @Valid @RequestBody DiagnoseCTInterpretationRequest diagnoseCTInterpretationRequest
    );

    @Operation(summary = "Find CT interpretation by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CT interpretation found!", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid CT interpretation id supplied!"),
            @ApiResponse(responseCode = "404", description = "CT interpretation not found!")
    })
    @GetMapping(path = "/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<CTInterpretationResponse> findById(@PathVariable long id);

    @Operation(summary = "Find all created CT interpretations by a specific radiologist.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CT interpretations returned!"),
            @ApiResponse(responseCode = "400", description = "Invalid radiologist id supplied!"),
            @ApiResponse(responseCode = "404", description = "Radiologist not found!")
    })
    @GetMapping(path = "/status/created/radiologist/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<CTInterpretationResponse>> findAllCreatedByRadiologistWithId(@PathVariable long id);

    @Operation(
            summary = "Find all created CT interpretations by a specific radiologist of patients with their full name containing a specific group of characters.",
            description = "Used for search bar - search by patient name through the list of CT interpretations made (with status CREATED) by the logged radiologist."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CT interpretations returned!"),
            @ApiResponse(responseCode = "400", description = "Invalid radiologist id supplied!"),
            @ApiResponse(responseCode = "404", description = "Radiologist not found!")
    })
    @GetMapping(path = "/status/created/radiologist/{id}/patient/{name}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<CTInterpretationResponse>> findAllCreatedByRadiologistIdAndPatientFullNameContainingIgnoreCase(
            @PathVariable("id") long radiologistId,
            @PathVariable String name
    );

    @Operation(summary = "Find all updated CT interpretations by a specific radiologist.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CT interpretations returned!"),
            @ApiResponse(responseCode = "400", description = "Invalid radiologist id supplied!"),
            @ApiResponse(responseCode = "404", description = "Radiologist not found!")
    })
    @GetMapping(path = "/status/updated/radiologist/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<CTInterpretationResponse>> findAllUpdatedByRadiologistWithId(@PathVariable("id") long radiologistId);

    @Operation(
            summary = "Find all updated CT interpretations by a specific radiologist of patients with their full name containing a specific group of characters.",
            description = "Used for search bar - search by patient name through the list of CT interpretations made and then updated (with status UPDATED) by the logged radiologist."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CT interpretations returned!"),
            @ApiResponse(responseCode = "400", description = "Invalid radiologist id supplied!"),
            @ApiResponse(responseCode = "404", description = "Radiologist not found!")
    })
    @GetMapping(path = "/status/updated/radiologist/{id}/patient/{name}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<CTInterpretationResponse>> findAllUpdatedByRadiologistIdAndPatientFullNameContainingIgnoreCase(
            @PathVariable("id") long radiologistId,
            @PathVariable String name
    );

    @Operation(summary = "Find all CT interpretations that need to be diagnosed by a specific doctor.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CT interpretations returned!"),
            @ApiResponse(responseCode = "400", description = "Invalid doctor id supplied!"),
            @ApiResponse(responseCode = "404", description = "Doctor not found!")
    })
    @GetMapping(path = "/status/to-diagnose/doctor/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<CTInterpretationResponse>> findAllToDiagnoseByDoctorWithId(@PathVariable("id") long doctorId);

    @Operation(summary = "Find all CT interpretations that need to be diagnosed by a specific doctor of patients with their full name containing a specific group of characters.",
            description = "Used for search bar - search by patient name through the list of CT interpretations that need to be diagnosed (with status TO_DIAGNOSE) by the logged doctor.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CT interpretations returned!"),
            @ApiResponse(responseCode = "400", description = "Invalid doctor id supplied!"),
            @ApiResponse(responseCode = "404", description = "Doctor not found!")
    })
    @GetMapping(path = "/status/to-diagnose/doctor/{id}/patient/{name}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<CTInterpretationResponse>> findAllToDiagnoseByDoctorIdAndPatientFullNameContainingIgnoreCase(
            @PathVariable("id") long doctorId,
            @PathVariable String name
    );

    @Operation(summary = "Find all CT interpretations diagnosed by a specific doctor.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CT interpretations returned!"),
            @ApiResponse(responseCode = "400", description = "Invalid doctor id supplied!"),
            @ApiResponse(responseCode = "404", description = "Doctor not found!")
    })
    @GetMapping(path = "/status/diagnosed/doctor/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<CTInterpretationResponse>> findAllDiagnosedByDoctorWithId(@PathVariable("id") long doctorId);

    @Operation(summary = "Find all CT interpretations with diagnosis of a specific patient.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CT interpretations returned!"),
            @ApiResponse(responseCode = "400", description = "Invalid patient id supplied!"),
            @ApiResponse(responseCode = "404", description = "Patient not found!")
    })
    @GetMapping(path = "/status/diagnosed/patient/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<CTInterpretationResponse>> findAllDiagnosedForPatientWithId(@PathVariable("id") long patientId);

    @Operation(summary = "Change the status of a CT interpretation. Status order: CREATED -> UPDATED -> TO_DIAGNOSE -> DIAGNOSED")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CT interpretations returned!"),
            @ApiResponse(responseCode = "400", description = "Invalid patient id supplied!"),
            @ApiResponse(responseCode = "403", description = "User is forbidden to access this resource!"),
            @ApiResponse(responseCode = "404", description = "Patient not found!")
    })
    @PutMapping("/id/{id}/status/{status}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<CTInterpretationResponse> changeStatus(
            @RequestParam("userId") long userId,
            @PathVariable("id") long ctInterpretationId,
            @PathVariable String status
    );

    @Operation(summary = "Delete an existing CT interpretation by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CT interpretation successfully deleted!"),
            @ApiResponse(responseCode = "400", description = "Invalid CT interpretation id supplied!"),
            @ApiResponse(responseCode = "403", description = "User is forbidden to access this resource!"),
            @ApiResponse(responseCode = "404", description = "CT interpretation not found!")
    })
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<String> deleteById(
            @RequestParam("userId") long userId,
            @RequestParam("interpretationId") long ctInterpretationId
    );
}
