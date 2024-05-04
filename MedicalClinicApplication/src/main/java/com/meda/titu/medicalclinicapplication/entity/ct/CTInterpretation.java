package com.meda.titu.medicalclinicapplication.entity.ct;

import com.meda.titu.medicalclinicapplication.entity.user.Doctor;
import com.meda.titu.medicalclinicapplication.entity.user.Patient;
import com.meda.titu.medicalclinicapplication.entity.user.Radiologist;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "interpretation_results")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CTInterpretation {
    @Id
    private long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ct_scan_id", unique = true)
    @MapsId
    private CTScan ctScan;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String prediction;
    @Column(name = "initial_interpretation", nullable = false)
    private String interpretation;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "radiologist_id")
    private Radiologist madeBy;
    @CreatedDate
    @Column(name = "created_on", updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;
    @Column(name = "final_diagnosis")
    private String finalDiagnosis;
    private String observations;
    @Enumerated(value = EnumType.STRING)
    private InterpretationStatus status;
}