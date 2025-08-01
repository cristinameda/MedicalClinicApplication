package com.meda.titu.medicalclinicapplication.service.user.impl;

import com.meda.titu.medicalclinicapplication.dto.request.user.UserRequest;
import com.meda.titu.medicalclinicapplication.dto.response.UserResponse;
import com.meda.titu.medicalclinicapplication.entity.user.Patient;
import com.meda.titu.medicalclinicapplication.exception.NotFoundException;
import com.meda.titu.medicalclinicapplication.mapper.user.PatientMapper;
import com.meda.titu.medicalclinicapplication.repository.user.DoctorRepository;
import com.meda.titu.medicalclinicapplication.repository.user.PatientRepository;
import com.meda.titu.medicalclinicapplication.service.user.UserService;
import com.meda.titu.medicalclinicapplication.util.UserEntityUpdater;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "PatientService")
@AllArgsConstructor
public class PatientServiceImpl implements UserService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final UserEntityUpdater userEntityUpdater;
    private final DoctorRepository doctorRepository;

    @Override
    public UserResponse save(UserRequest patientRequest) {
        Patient patient = patientMapper.userRequestToPatient(patientRequest);
        userEntityUpdater.updatePassword(patient, patientRequest.getPassword());
        return patientMapper.patientToUserResponse(patientRepository.save(patient));
    }

    @Override
    public UserResponse update(long id, UserRequest patientRequest) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patient with id " + id +
                        " could not be found!"));
        userEntityUpdater.updateUserEntity(patient, patientRequest);
        return patientMapper.patientToUserResponse(patientRepository.save(patient));
    }

    @Override
    public UserResponse findById(long id) {
        return patientRepository.findById(id)
                .map(patientMapper::patientToUserResponse)
                .orElseThrow(() -> new NotFoundException("Patient with id " + id +
                        " could not be found!"));
    }

    @Override
    public UserResponse findByUsername(String username) {
        return patientRepository.findByUsername(username)
                .map(patientMapper::patientToUserResponse)
                .orElseThrow(() -> new NotFoundException("Patient with username " + username +
                        " could not be found!"));
    }

    @Override
    public List<UserResponse> findAll() {
        List<Patient> patients = patientRepository.findAll();
        return patientMapper.patientsToUserResponses(patients);
    }

    @Override
    public List<UserResponse> findAllWithFullNameContaining(String word) {
        List<Patient> patients = patientRepository.findAllByFullNameContainingIgnoreCase(word);
        return patientMapper.patientsToUserResponses(patients);
    }

    @Override
    public void deleteById(long id) {
        patientRepository.deleteById(id);
    }
}
