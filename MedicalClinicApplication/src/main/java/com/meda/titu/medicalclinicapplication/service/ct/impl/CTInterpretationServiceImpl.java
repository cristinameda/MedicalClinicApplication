package com.meda.titu.medicalclinicapplication.service.ct.impl;

import com.meda.titu.medicalclinicapplication.dto.request.ct.CTScanRequest;
import com.meda.titu.medicalclinicapplication.dto.request.ct.CreateCTInterpretationRequest;
import com.meda.titu.medicalclinicapplication.dto.request.ct.DiagnoseCTInterpretationRequest;
import com.meda.titu.medicalclinicapplication.dto.request.ct.UpdateCTInterpretationRequest;
import com.meda.titu.medicalclinicapplication.dto.response.ct.CTInterpretationResponse;
import com.meda.titu.medicalclinicapplication.entity.ct.CTInterpretation;
import com.meda.titu.medicalclinicapplication.entity.ct.InterpretationStatus;
import com.meda.titu.medicalclinicapplication.exception.CorruptedFileException;
import com.meda.titu.medicalclinicapplication.exception.IllegalStatusException;
import com.meda.titu.medicalclinicapplication.exception.IllegalStatusUpdateOrderException;
import com.meda.titu.medicalclinicapplication.exception.IllegalUserAccessException;
import com.meda.titu.medicalclinicapplication.exception.NotFoundException;
import com.meda.titu.medicalclinicapplication.mapper.ct.CTInterpretationMapper;
import com.meda.titu.medicalclinicapplication.repository.ct.CTInterpretationRepository;
import com.meda.titu.medicalclinicapplication.service.ct.CTInterpretationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CTInterpretationServiceImpl implements CTInterpretationService {

    private final CTInterpretationRepository ctInterpretationRepository;
    private final CTInterpretationMapper ctInterpretationMapper;

    @Override
    public CTInterpretationResponse save(CreateCTInterpretationRequest createCTInterpretationRequest, MultipartFile ctImage) {
        if (ctImage == null) {
            throw new NullPointerException("CT image not passed in the multipart request!");
        }
        CTScanRequest ctScanRequest = getCTScanRequest(ctImage, createCTInterpretationRequest.getScannedOn());
        // TODO: call python API and set prediction field
        CTInterpretation ctInterpretation = ctInterpretationMapper.createRequestToEntity(createCTInterpretationRequest, ctScanRequest);
        return ctInterpretationMapper.entityToResponse(ctInterpretationRepository.save(ctInterpretation));
    }

    @Override
    public CTInterpretationResponse update(long ctInterpretationId, UpdateCTInterpretationRequest updateCTInterpretationRequest) {
        CTInterpretation ctInterpretation = getCtInterpretation(ctInterpretationId);
        checkCTInterpretationStatus(ctInterpretation, InterpretationStatus.CREATED);
        checkIfCTInterpretationIsMadeByUserId(ctInterpretation, updateCTInterpretationRequest.getUserId());
        updateCTInterpretation(ctInterpretation, updateCTInterpretationRequest);
        ctInterpretation.setStatus(InterpretationStatus.UPDATED);
        return ctInterpretationMapper.entityToResponse(
                ctInterpretationRepository.save(ctInterpretation)
        );
    }

    @Override
    public CTInterpretationResponse diagnose(long ctInterpretationId, DiagnoseCTInterpretationRequest diagnoseCTInterpretationRequest) {
        CTInterpretation ctInterpretation = getCtInterpretation(ctInterpretationId);
        checkCTInterpretationStatus(ctInterpretation, InterpretationStatus.TO_DIAGNOSE);
        checkIfCTInterpretationHasDoctorId(ctInterpretation, diagnoseCTInterpretationRequest.getUserId());
        updateCTInterpretation(ctInterpretation, diagnoseCTInterpretationRequest);
        ctInterpretation.setStatus(InterpretationStatus.DIAGNOSED);
        return ctInterpretationMapper.entityToResponse(
                ctInterpretationRepository.save(ctInterpretation)
        );
    }

    @Override
    public CTInterpretationResponse findById(long ctInterpretationId) {
        return ctInterpretationRepository.findById(ctInterpretationId)
                .map(ctInterpretationMapper::entityToResponse)
                .orElseThrow(() -> new NotFoundException("CT interpretation with id " + ctInterpretationId +
                        " could not be found!"));
    }

    @Override
    public List<CTInterpretationResponse> findAllByRadiologistIdAndStatus(long radiologistId, InterpretationStatus status) {
        return ctInterpretationRepository.findAllByMadeByIdAndStatus(radiologistId, status)
                .stream()
                .map(ctInterpretationMapper::entityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CTInterpretationResponse> findAllByPatientIdAndStatus(long patientId, InterpretationStatus status) {
        return ctInterpretationRepository.findAllByPatientIdAndStatus(patientId, status)
                .stream()
                .map(ctInterpretationMapper::entityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CTInterpretationResponse> findAllByDoctorIdAndStatus(long doctorId, InterpretationStatus status) {
        return ctInterpretationRepository.findAllByDoctorIdAndStatus(doctorId, status)
                .stream()
                .map(ctInterpretationMapper::entityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CTInterpretationResponse> findAllByRadiologistIdAndStatusAndPatientFullNameContainingIgnoreCase(
            long radiologistId,
            InterpretationStatus status,
            String word
    ) {
        return ctInterpretationRepository.findAllByMadeByIdAndStatusAndPatientFullNameContainingIgnoreCase(radiologistId, status, word)
                .stream()
                .map(ctInterpretationMapper::entityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CTInterpretationResponse> findAllByDoctorIdAndStatusAndPatientFullNameContainingIgnoreCase(
            long doctorId,
            InterpretationStatus status,
            String word
    ) {
        return ctInterpretationRepository.findAllByDoctorIdAndStatusAndPatientFullNameContainingIgnoreCase(doctorId, status, word)
                .stream()
                .map(ctInterpretationMapper::entityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CTInterpretationResponse changeStatus(long userId, long ctInterpretationId, String status) {
        CTInterpretation ctInterpretation = getCtInterpretation(ctInterpretationId);
        checkIfUserIsPartOfInterpretationMedicalStaff(userId, ctInterpretation);
        var currStatus = ctInterpretation.getStatus();
        var newStatus = InterpretationStatus.fromValue(status);
        checkIfGoodUpdateOrder(currStatus, newStatus);
        ctInterpretation.setStatus(newStatus);
        return ctInterpretationMapper.entityToResponse(ctInterpretationRepository.save(ctInterpretation));
    }

    @Override
    public void deleteById(long userId, long ctInterpretationId) {
        CTInterpretation ctInterpretation = getCtInterpretation(ctInterpretationId);
        checkIfUserIsPartOfInterpretationMedicalStaff(userId, ctInterpretation);
        ctInterpretationRepository.deleteById(ctInterpretationId);
    }

    private CTInterpretation getCtInterpretation(long ctInterpretationId) {
        return ctInterpretationRepository.findById(ctInterpretationId)
                .orElseThrow(() -> new NotFoundException("CT interpretation with id " + ctInterpretationId +
                        " could not be found!"));
    }

    private void checkCTInterpretationStatus(CTInterpretation ctInterpretation, InterpretationStatus status) {
        if (!ctInterpretation.getStatus().equals(status)) {
            throw new IllegalStatusException("The status of the CT interpretation with id " + ctInterpretation.getId() + " must be " + status + " for it to be eligible for this operation!");
        }
    }

    private void checkIfCTInterpretationIsMadeByUserId(CTInterpretation ctInterpretation, long userId) {
        var radiologistId = ctInterpretation.getMadeBy().getId();
        if (userId != radiologistId) {
            throw new IllegalUserAccessException("User with id " + userId + " is not allowed to access this resource!");
        }
    }

    private void checkIfCTInterpretationHasDoctorId(CTInterpretation ctInterpretation, long userId) {
        var doctorId = ctInterpretation.getDoctor().getId();
        if (userId != doctorId) {
            throw new IllegalUserAccessException("User with id " + userId + " is not allowed to access this resource!");
        }
    }

    private void checkIfUserIsPartOfInterpretationMedicalStaff(long userId, CTInterpretation ctInterpretation) {
        checkIfCTInterpretationIsMadeByUserId(ctInterpretation, userId);
        checkIfCTInterpretationHasDoctorId(ctInterpretation, userId);
    }

    private CTScanRequest getCTScanRequest(MultipartFile CTImage, LocalDateTime scannedOn) {
        try {
            String ctImageName = StringUtils.cleanPath(Objects.requireNonNull(CTImage.getOriginalFilename()));
            return CTScanRequest.builder()
                    .name(ctImageName)
                    .type(CTImage.getContentType())
                    .content(CTImage.getBytes())
                    .scannedOn(scannedOn)
                    .build();
        } catch (IOException e) {
            throw new CorruptedFileException("Invalid CT image!");
        }
    }

    private void updateCTInterpretation(CTInterpretation ctInterpretation, UpdateCTInterpretationRequest updatedCTInterpretation) {
        ctInterpretation.setDescription(updatedCTInterpretation.getDescription());
        ctInterpretation.setInterpretation(updatedCTInterpretation.getInterpretation());
    }

    private void updateCTInterpretation(CTInterpretation ctInterpretation, DiagnoseCTInterpretationRequest diagnoseCTInterpretationRequest) {
        ctInterpretation.setFinalDiagnosis(diagnoseCTInterpretationRequest.getFinalDiagnosis());
        ctInterpretation.setObservations(diagnoseCTInterpretationRequest.getObservations());
    }

    private void checkIfGoodUpdateOrder(InterpretationStatus currStatus, InterpretationStatus newStatus) {
        if (!currStatus.hasNext(newStatus)) {
            throw new IllegalStatusUpdateOrderException("Cannot downgrade status!" +
                    "Current status needs to be prior new status (CREATED -> UPDATED -> TO_DIAGNOSE -> DIAGNOSED)." +
                    "Current status: " + currStatus + ". New status: " + newStatus);
        }
    }
}
