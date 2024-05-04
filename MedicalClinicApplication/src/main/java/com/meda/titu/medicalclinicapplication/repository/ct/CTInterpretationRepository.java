package com.meda.titu.medicalclinicapplication.repository.ct;

import com.meda.titu.medicalclinicapplication.entity.ct.CTInterpretation;
import com.meda.titu.medicalclinicapplication.entity.ct.InterpretationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CTInterpretationRepository extends JpaRepository<CTInterpretation, Long> {
    List<CTInterpretation> findAllByMadeByIdAndStatus(long madeById, InterpretationStatus status);

    List<CTInterpretation> findAllByPatientIdAndStatus(long patientId, InterpretationStatus status);

    List<CTInterpretation> findAllByDoctorIdAndStatus(long doctorId, InterpretationStatus status);

    List<CTInterpretation> findAllByMadeByIdAndStatusAndPatientFullNameContainingIgnoreCase(long madeById, InterpretationStatus status, String word);

    List<CTInterpretation> findAllByDoctorIdAndStatusAndPatientFullNameContainingIgnoreCase(long doctorId, InterpretationStatus status, String word);
}
