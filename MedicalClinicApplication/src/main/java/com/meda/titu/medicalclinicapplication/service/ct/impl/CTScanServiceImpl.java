package com.meda.titu.medicalclinicapplication.service.ct.impl;

import com.meda.titu.medicalclinicapplication.dto.request.ct.CTScanRequest;
import com.meda.titu.medicalclinicapplication.dto.response.ct.CTScanResponse;
import com.meda.titu.medicalclinicapplication.entity.ct.CTScan;
import com.meda.titu.medicalclinicapplication.exception.NotFoundException;
import com.meda.titu.medicalclinicapplication.mapper.ct.CTScanMapper;
import com.meda.titu.medicalclinicapplication.repository.ct.CTScanRepository;
import com.meda.titu.medicalclinicapplication.service.ct.CTScanService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CTScanServiceImpl implements CTScanService {

    private final CTScanRepository ctScanRepository;
    private final CTScanMapper ctScanMapper;

    @Override
    public CTScanResponse save(CTScanRequest ctScanRequest) {
        CTScan ctScan = ctScanMapper.ctScanRequestToCTScan(ctScanRequest);
        return ctScanMapper.ctScanToCTScanResponse(ctScanRepository.save(ctScan));
    }

    @Override
    public CTScanResponse findById(long id) {
        return ctScanRepository.findById(id)
                .map(ctScanMapper::ctScanToCTScanResponse)
                .orElseThrow(() -> new NotFoundException("CT scan with id " + id +
                        " could not be found!"));
    }

    @Override
    public List<CTScanResponse> findAll() {
        List<CTScan> ctScans = ctScanRepository.findAll();
        return ctScanMapper.ctScansToCTScanResponses(ctScans);
    }

    @Override
    public void deleteById(long id) {
        ctScanRepository.deleteById(id);
    }
}
