package com.meda.titu.medicalclinicapplication.service.ct.impl;

import com.meda.titu.medicalclinicapplication.dto.request.ct.CTScanRequest;
import com.meda.titu.medicalclinicapplication.dto.request.ct.CreateCTInterpretationRequest;
import com.meda.titu.medicalclinicapplication.dto.request.ct.UpdateCTInterpretationRequest;
import com.meda.titu.medicalclinicapplication.dto.response.ct.CTInterpretationResponse;
import com.meda.titu.medicalclinicapplication.entity.ct.CTInterpretation;
import com.meda.titu.medicalclinicapplication.entity.ct.InterpretationStatus;
import com.meda.titu.medicalclinicapplication.exception.CorruptedFileException;
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
        CTInterpretation ctInterpretation = ctInterpretationRepository.findById(ctInterpretationId)
                .orElseThrow(() -> new NotFoundException(
                        "CT interpretation with id " + ctInterpretationId + " could not be found!"));
        updateCTInterpretation(ctInterpretation, updateCTInterpretationRequest);
        ctInterpretation.setStatus(InterpretationStatus.UPDATED);
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
    public List<CTInterpretationResponse> findAllByRadiologistIdAndStatusAndPatientFullNameContainingIgnoreCase(long madeById, InterpretationStatus status, String word) {
        return ctInterpretationRepository.findAllByMadeByIdAndStatusAndPatientFullNameContainingIgnoreCase(madeById, status, word)
                .stream()
                .map(ctInterpretationMapper::entityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CTInterpretationResponse> findAllByDoctorIdAndStatusAndPatientFullNameContainingIgnoreCase(long doctorId, InterpretationStatus status, String word) {
        return ctInterpretationRepository.findAllByDoctorIdAndStatusAndPatientFullNameContainingIgnoreCase(doctorId, status, word)
                .stream()
                .map(ctInterpretationMapper::entityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CTInterpretationResponse changeStatus(long ctInterpretationId, String status) {
        CTInterpretation ctInterpretation = ctInterpretationRepository.findById(ctInterpretationId)
                .orElseThrow(() -> new NotFoundException("CT interpretation with id " + ctInterpretationId +
                        " could not be found!"));
        ctInterpretation.setStatus(InterpretationStatus.fromValue(status));
        return ctInterpretationMapper.entityToResponse(ctInterpretationRepository.save(ctInterpretation));
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
        ctInterpretation.setFinalDiagnosis(updatedCTInterpretation.getFinalDiagnosis());
        ctInterpretation.setObservations(updatedCTInterpretation.getObservations());
    }
}
