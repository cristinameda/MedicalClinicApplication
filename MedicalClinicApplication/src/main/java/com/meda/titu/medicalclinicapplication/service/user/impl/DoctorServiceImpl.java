package com.meda.titu.medicalclinicapplication.service.user.impl;

import com.meda.titu.medicalclinicapplication.dto.request.user.UserRequest;
import com.meda.titu.medicalclinicapplication.dto.response.UserResponse;
import com.meda.titu.medicalclinicapplication.entity.user.Doctor;
import com.meda.titu.medicalclinicapplication.exception.NotFoundException;
import com.meda.titu.medicalclinicapplication.mapper.user.DoctorMapper;
import com.meda.titu.medicalclinicapplication.repository.user.DoctorRepository;
import com.meda.titu.medicalclinicapplication.service.user.UserService;
import com.meda.titu.medicalclinicapplication.util.UserEntityUpdater;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "DoctorService")
@AllArgsConstructor
public class DoctorServiceImpl implements UserService {
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final UserEntityUpdater userEntityUpdater;

    @Override
    public UserResponse save(UserRequest doctorRequest) {
        Doctor doctor = doctorMapper.userRequestToDoctor(doctorRequest);
        userEntityUpdater.updatePassword(doctor, doctorRequest.getPassword());
        return doctorMapper.doctorToUserResponse(doctorRepository.save(doctor));
    }

    @Override
    public UserResponse update(long id, UserRequest doctorRequest) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Doctor with id " + id +
                        " could not be found!"));
        userEntityUpdater.updateUserEntity(doctor, doctorRequest);
        return doctorMapper.doctorToUserResponse(doctorRepository.save(doctor));
    }

    @Override
    public UserResponse findById(long id) {
        return doctorRepository.findById(id)
                .map(doctorMapper::doctorToUserResponse)
                .orElseThrow(() -> new NotFoundException("Doctor with id " + id +
                        " could not be found!"));
    }

    @Override
    public UserResponse findByUsername(String username) {
        return doctorRepository.findByUsername(username)
                .map(doctorMapper::doctorToUserResponse)
                .orElseThrow(() -> new NotFoundException("Doctor with username " + username +
                        " could not be found!"));
    }

    @Override
    public List<UserResponse> findAll() {
        List<Doctor> doctors = doctorRepository.findAll();
        return doctorMapper.doctorsToUserResponses(doctors);
    }

    @Override
    public List<UserResponse> findAllWithFullNameContaining(String word) {
        List<Doctor> doctors = doctorRepository.findAllByFullNameContainingIgnoreCase(word);
        return doctorMapper.doctorsToUserResponses(doctors);
    }

    @Override
    public void deleteById(long id) {
        doctorRepository.deleteById(id);
    }
}
