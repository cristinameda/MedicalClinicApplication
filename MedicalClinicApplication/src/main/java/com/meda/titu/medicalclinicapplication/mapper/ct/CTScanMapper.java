package com.meda.titu.medicalclinicapplication.mapper.ct;

import com.meda.titu.medicalclinicapplication.dto.request.ct.CTScanRequest;
import com.meda.titu.medicalclinicapplication.dto.response.ct.CTScanResponse;
import com.meda.titu.medicalclinicapplication.entity.ct.CTScan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface CTScanMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ctInterpretation", ignore = true)
    CTScan ctScanRequestToCTScan(CTScanRequest ctScanRequest);

    CTScanResponse ctScanToCTScanResponse(CTScan ctScan);

    @Mapping(target = "ctInterpretation", ignore = true)
    CTScan ctScanResponseToCTScan(CTScanResponse ctScanResponse);

    List<CTScanResponse> ctScansToCTScanResponses(List<CTScan> ctScans);
}
