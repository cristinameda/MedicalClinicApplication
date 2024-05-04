package com.meda.titu.medicalclinicapplication.dto.request.ct;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCTInterpretationRequest {
    @NotBlank
    private String description;
    @NotBlank
    private String interpretation;
    @NotBlank
    private String finalDiagnosis;
    @NotBlank
    private String observations;
}
