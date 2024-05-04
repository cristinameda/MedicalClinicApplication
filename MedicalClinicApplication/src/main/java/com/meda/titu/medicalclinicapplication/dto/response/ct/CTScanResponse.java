package com.meda.titu.medicalclinicapplication.dto.response.ct;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CTScanResponse {
    private long id;
    private String name;
    private String type;
    private byte[] content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime scannedOn;
}
