package com.meda.titu.medicalclinicapplication.service.ct;

import com.meda.titu.medicalclinicapplication.dto.request.ct.CTScanRequest;
import com.meda.titu.medicalclinicapplication.dto.response.ct.CTScanResponse;

import java.util.List;

public interface CTScanService {
    CTScanResponse save(CTScanRequest ctScanRequest);

    CTScanResponse findById(long id);

    List<CTScanResponse> findAll();

    void deleteById(long id);
}
