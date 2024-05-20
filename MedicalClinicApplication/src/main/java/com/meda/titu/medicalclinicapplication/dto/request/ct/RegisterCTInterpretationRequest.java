package com.meda.titu.medicalclinicapplication.dto.request.ct;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterCTInterpretationRequest {
    @Valid
    @NotNull
    private CreateCTInterpretationRequest createCTInterpretationRequest;
    @NotNull
    private MultipartFile ctScan;
}
