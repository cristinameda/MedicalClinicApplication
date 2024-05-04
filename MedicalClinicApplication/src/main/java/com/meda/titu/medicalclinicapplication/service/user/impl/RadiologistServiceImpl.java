package com.meda.titu.medicalclinicapplication.service.user.impl;

import com.meda.titu.medicalclinicapplication.dto.request.user.UserRequest;
import com.meda.titu.medicalclinicapplication.dto.response.UserResponse;
import com.meda.titu.medicalclinicapplication.entity.user.Radiologist;
import com.meda.titu.medicalclinicapplication.exception.NotFoundException;
import com.meda.titu.medicalclinicapplication.mapper.user.RadiologistMapper;
import com.meda.titu.medicalclinicapplication.repository.user.RadiologistRepository;
import com.meda.titu.medicalclinicapplication.service.user.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "RadiologistService")
public class RadiologistServiceImpl implements UserService {
    private final RadiologistRepository radiologistRepository;
    private final RadiologistMapper radiologistMapper;
    private final PasswordEncoder passwordEncoder;

    public RadiologistServiceImpl(RadiologistRepository radiologistRepository, RadiologistMapper radiologistMapper, PasswordEncoder passwordEncoder) {
        this.radiologistRepository = radiologistRepository;
        this.radiologistMapper = radiologistMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse save(UserRequest radiologistRequest) {
        Radiologist radiologist = radiologistMapper.userRequestToRadiologist(radiologistRequest);
        radiologist.setPassword(passwordEncoder.encode(radiologist.getPassword()));
        return radiologistMapper.radiologistToUserResponse(radiologistRepository.save(radiologist));
    }

    @Override
    public UserResponse findById(long id) {
        return radiologistRepository.findById(id)
                .map(radiologistMapper::radiologistToUserResponse)
                .orElseThrow(() -> new NotFoundException("Radiologist with id " + id +
                        " could not be found!"));
    }

    @Override
    public UserResponse findByUsername(String username) {
        return radiologistRepository.findByUsername(username)
                .map(radiologistMapper::radiologistToUserResponse)
                .orElseThrow(() -> new NotFoundException("Radiologist with username " + username +
                        " could not be found!"));
    }

    @Override
    public List<UserResponse> findAll() {
        List<Radiologist> radiologists = radiologistRepository.findAll();
        return radiologistMapper.radiologistsToUserResponses(radiologists);
    }

    @Override
    public List<UserResponse> findAllWithFullNameContaining(String word) {
        List<Radiologist> radiologists = radiologistRepository.findAllByFullNameContainingIgnoreCase(word);
        return radiologistMapper.radiologistsToUserResponses(radiologists);
    }

    @Override
    public void deleteById(long id) {
        radiologistRepository.deleteById(id);
    }
}
