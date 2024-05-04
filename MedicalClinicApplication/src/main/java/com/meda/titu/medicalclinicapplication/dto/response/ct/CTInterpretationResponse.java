package com.meda.titu.medicalclinicapplication.dto.response.ct;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.meda.titu.medicalclinicapplication.dto.response.UserResponse;
import com.meda.titu.medicalclinicapplication.entity.ct.InterpretationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CTInterpretationResponse {
    private CTScanResponse ctScan;
    private UserResponse patient;
    private UserResponse doctor;
    private String description;
    private String prediction;
    private String interpretation;
    private UserResponse madeBy;
    private String finalDiagnosis;
    private String observations;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private InterpretationStatus status;
}
