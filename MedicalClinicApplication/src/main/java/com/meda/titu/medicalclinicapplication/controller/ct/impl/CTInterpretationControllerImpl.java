package com.meda.titu.medicalclinicapplication.controller.ct.impl;

import com.meda.titu.medicalclinicapplication.controller.ct.CTInterpretationController;
import com.meda.titu.medicalclinicapplication.dto.request.ct.CreateCTInterpretationRequest;
import com.meda.titu.medicalclinicapplication.dto.request.ct.DiagnoseCTInterpretationRequest;
import com.meda.titu.medicalclinicapplication.dto.request.ct.UpdateCTInterpretationRequest;
import com.meda.titu.medicalclinicapplication.dto.response.ct.CTInterpretationResponse;
import com.meda.titu.medicalclinicapplication.entity.ct.InterpretationStatus;
import com.meda.titu.medicalclinicapplication.service.ct.CTInterpretationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/ct-interpretations")
@AllArgsConstructor
public class CTInterpretationControllerImpl implements CTInterpretationController {

    private final CTInterpretationService ctInterpretationService;

    @Override
    public ResponseEntity<CTInterpretationResponse> save(CreateCTInterpretationRequest createCTInterpretationRequest, MultipartFile ctScan) {
        return new ResponseEntity<>(
                ctInterpretationService.save(createCTInterpretationRequest, ctScan),
                HttpStatus.CREATED
        );
    }

    @Override
    public ResponseEntity<CTInterpretationResponse> update(long ctInterpretationId, UpdateCTInterpretationRequest updateCTInterpretationRequest) {
        return new ResponseEntity<>(
                ctInterpretationService.update(ctInterpretationId, updateCTInterpretationRequest),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<CTInterpretationResponse> diagnose(long ctInterpretationId, DiagnoseCTInterpretationRequest diagnoseCTInterpretationRequest) {
        return new ResponseEntity<>(
                ctInterpretationService.diagnose(ctInterpretationId, diagnoseCTInterpretationRequest),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<CTInterpretationResponse> findById(long ctInterpretationId) {
        return new ResponseEntity<>(ctInterpretationService.findById(ctInterpretationId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CTInterpretationResponse>> findAllCreatedByRadiologistWithId(long radiologistId) {
        return new ResponseEntity<>(
                ctInterpretationService.findAllByRadiologistIdAndStatus(radiologistId, InterpretationStatus.CREATED),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<List<CTInterpretationResponse>> findAllCreatedByRadiologistIdAndPatientFullNameContainingIgnoreCase(long radiologistId, String name) {
        return new ResponseEntity<>(
                ctInterpretationService.findAllByRadiologistIdAndStatusAndPatientFullNameContainingIgnoreCase(
                        radiologistId,
                        InterpretationStatus.CREATED,
                        name
                ),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<List<CTInterpretationResponse>> findAllUpdatedByRadiologistWithId(long radiologistId) {
        return new ResponseEntity<>(
                ctInterpretationService.findAllByRadiologistIdAndStatus(radiologistId, InterpretationStatus.UPDATED),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<List<CTInterpretationResponse>> findAllUpdatedByRadiologistIdAndPatientFullNameContainingIgnoreCase(long radiologistId, String name) {
        return new ResponseEntity<>(
                ctInterpretationService.findAllByRadiologistIdAndStatusAndPatientFullNameContainingIgnoreCase(
                        radiologistId,
                        InterpretationStatus.UPDATED,
                        name
                ),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<List<CTInterpretationResponse>> findAllToDiagnoseByDoctorWithId(long doctorId) {
        return new ResponseEntity<>(
                ctInterpretationService.findAllByDoctorIdAndStatus(doctorId, InterpretationStatus.TO_DIAGNOSE),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<List<CTInterpretationResponse>> findAllToDiagnoseByDoctorIdAndPatientFullNameContainingIgnoreCase(long doctorId, String name) {
        return new ResponseEntity<>(
                ctInterpretationService.findAllByDoctorIdAndStatusAndPatientFullNameContainingIgnoreCase(
                        doctorId,
                        InterpretationStatus.TO_DIAGNOSE,
                        name
                ),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<List<CTInterpretationResponse>> findAllDiagnosedByDoctorWithId(long doctorId) {
        return new ResponseEntity<>(
                ctInterpretationService.findAllByDoctorIdAndStatus(doctorId, InterpretationStatus.DIAGNOSED),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<List<CTInterpretationResponse>> findAllDiagnosedForPatientWithId(long patientId) {
        return new ResponseEntity<>(
                ctInterpretationService.findAllByPatientIdAndStatus(patientId, InterpretationStatus.DIAGNOSED),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<CTInterpretationResponse> changeStatus(long userId, long ctInterpretationId, String status) {
        return new ResponseEntity<>(
                ctInterpretationService.changeStatus(userId, ctInterpretationId, status),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<String> deleteById(long userId, long ctInterpretationId) {
        ctInterpretationService.deleteById(userId, ctInterpretationId);
        return new ResponseEntity<>("CT interpretation with id " + ctInterpretationId + " successfully deleted!", HttpStatus.OK);
    }
}
