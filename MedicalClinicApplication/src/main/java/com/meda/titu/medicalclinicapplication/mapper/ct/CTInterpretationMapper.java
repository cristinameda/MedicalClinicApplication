package com.meda.titu.medicalclinicapplication.mapper.ct;

import com.meda.titu.medicalclinicapplication.dto.request.ct.CTScanRequest;
import com.meda.titu.medicalclinicapplication.dto.request.ct.CreateCTInterpretationRequest;
import com.meda.titu.medicalclinicapplication.dto.response.ct.CTInterpretationResponse;
import com.meda.titu.medicalclinicapplication.entity.ct.CTInterpretation;
import com.meda.titu.medicalclinicapplication.entity.ct.CTScan;
import com.meda.titu.medicalclinicapplication.entity.ct.InterpretationStatus;
import com.meda.titu.medicalclinicapplication.entity.user.Doctor;
import com.meda.titu.medicalclinicapplication.entity.user.Patient;
import com.meda.titu.medicalclinicapplication.entity.user.Radiologist;
import com.meda.titu.medicalclinicapplication.mapper.user.DoctorMapper;
import com.meda.titu.medicalclinicapplication.mapper.user.PatientMapper;
import com.meda.titu.medicalclinicapplication.mapper.user.RadiologistMapper;
import com.meda.titu.medicalclinicapplication.service.user.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CTInterpretationMapper {

    private final UserService patientService;
    private final UserService doctorService;
    private final UserService radiologistService;
    private final CTScanMapper ctScanMapper;
    private final PatientMapper patientMapper;
    private final DoctorMapper doctorMapper;
    private final RadiologistMapper radiologistMapper;

    public CTInterpretationMapper(
            @Qualifier("PatientService") UserService patientService,
            @Qualifier("DoctorService") UserService doctorService,
            @Qualifier("RadiologistService") UserService radiologistService,
            CTScanMapper ctScanMapper,
            PatientMapper patientMapper,
            DoctorMapper doctorMapper,
            RadiologistMapper radiologistMapper) {
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.radiologistService = radiologistService;
        this.ctScanMapper = ctScanMapper;
        this.patientMapper = patientMapper;
        this.doctorMapper = doctorMapper;
        this.radiologistMapper = radiologistMapper;
    }

    public CTInterpretation createRequestToEntity(CreateCTInterpretationRequest createCtInterpretationRequest, CTScanRequest ctScanRequest) {
        CTScan ctScan = ctScanMapper.ctScanRequestToCTScan(ctScanRequest);
        Patient patient = getPatientById(createCtInterpretationRequest.getPatientId());
        Doctor doctor = getDoctorById(createCtInterpretationRequest.getDoctorId());
        Radiologist radiologist = getRadiologistById(createCtInterpretationRequest.getMadeBy());

        return CTInterpretation.builder()
                .ctScan(ctScan)
                .patient(patient)
                .doctor(doctor)
                .description(createCtInterpretationRequest.getDescription())
                .prediction("-")
                .interpretation(createCtInterpretationRequest.getInterpretation())
                .madeBy(radiologist)
                .createdOn(null)
                .finalDiagnosis("-")
                .observations("-")
                .status(InterpretationStatus.CREATED)
                .build();
    }

    public CTInterpretationResponse entityToResponse(CTInterpretation ctInterpretation) {
        return CTInterpretationResponse.builder()
                .ctScan(ctScanMapper.ctScanToCTScanResponse(ctInterpretation.getCtScan()))
                .patient(patientService.findById(ctInterpretation.getPatient().getId()))
                .doctor(doctorService.findById(ctInterpretation.getDoctor().getId()))
                .description(ctInterpretation.getDescription())
                .prediction(ctInterpretation.getPrediction())
                .interpretation(ctInterpretation.getInterpretation())
                .madeBy(radiologistService.findById(ctInterpretation.getMadeBy().getId()))
                .finalDiagnosis(ctInterpretation.getFinalDiagnosis())
                .observations(ctInterpretation.getObservations())
                .status(ctInterpretation.getStatus())
                .build();
    }

    private Patient getPatientById(long id) {
        return patientMapper.userResponseToPatient(
                patientService.findById(id)
        );
    }

    private Doctor getDoctorById(long id) {
        return doctorMapper.userResponseToDoctor(
                doctorService.findById(id)
        );
    }

    private Radiologist getRadiologistById(long id) {
        return radiologistMapper.userResponseToRadiologist(
                radiologistService.findById(id)
        );
    }
}
