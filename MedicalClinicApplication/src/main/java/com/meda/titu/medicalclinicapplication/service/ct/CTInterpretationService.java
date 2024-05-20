package com.meda.titu.medicalclinicapplication.service.ct;

import com.meda.titu.medicalclinicapplication.dto.request.ct.CreateCTInterpretationRequest;
import com.meda.titu.medicalclinicapplication.dto.request.ct.DiagnoseCTInterpretationRequest;
import com.meda.titu.medicalclinicapplication.dto.request.ct.UpdateCTInterpretationRequest;
import com.meda.titu.medicalclinicapplication.dto.response.ct.CTInterpretationResponse;
import com.meda.titu.medicalclinicapplication.entity.ct.InterpretationStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CTInterpretationService {

    CTInterpretationResponse save(CreateCTInterpretationRequest createCTInterpretationRequest, MultipartFile ctScan);

    CTInterpretationResponse update(long ctInterpretationId, UpdateCTInterpretationRequest updateCTInterpretationRequest);

    CTInterpretationResponse diagnose(long ctInterpretationId, DiagnoseCTInterpretationRequest diagnoseCTInterpretationRequest);

    CTInterpretationResponse findById(long ctInterpretationId);

    List<CTInterpretationResponse> findAllByRadiologistIdAndStatus(long radiologistId, InterpretationStatus status);

    List<CTInterpretationResponse> findAllByPatientIdAndStatus(long patientId, InterpretationStatus status);

    List<CTInterpretationResponse> findAllByDoctorIdAndStatus(long doctorId, InterpretationStatus status);

    List<CTInterpretationResponse> findAllByRadiologistIdAndStatusAndPatientFullNameContainingIgnoreCase(long radiologistId, InterpretationStatus status, String word);

    List<CTInterpretationResponse> findAllByDoctorIdAndStatusAndPatientFullNameContainingIgnoreCase(long doctorId, InterpretationStatus status, String word);

    CTInterpretationResponse changeStatus(long userId, long ctInterpretationId, String status);

    void deleteById(long userId, long ctInterpretationId);
}
