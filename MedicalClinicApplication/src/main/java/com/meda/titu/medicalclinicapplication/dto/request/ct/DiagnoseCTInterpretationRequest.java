package com.meda.titu.medicalclinicapplication.dto.request.ct;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiagnoseCTInterpretationRequest {
    @NotNull
    @Positive
    private long userId;
    @NotBlank
    private String finalDiagnosis;
    @NotBlank
    private String observations;
}
