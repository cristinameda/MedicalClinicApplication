package com.meda.titu.medicalclinicapplication.dto.request.ct;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCTInterpretationRequest {
    @NotNull
    @Positive
    private long patientId;
    @NotNull
    @Positive
    private long doctorId;
    @NotBlank
    private String description;
    @NotBlank
    private String interpretation;
    @NotNull
    @Positive
    private long madeBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime scannedOn;
}
