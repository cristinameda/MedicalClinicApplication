package com.meda.titu.medicalclinicapplication.mapper.user;

import com.meda.titu.medicalclinicapplication.dto.request.user.UserRequest;
import com.meda.titu.medicalclinicapplication.dto.response.UserResponse;
import com.meda.titu.medicalclinicapplication.entity.user.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface DoctorMapper {
    @Mapping(target = "interpretations", ignore = true)
    @Mapping(target = "id", ignore = true)
    Doctor userRequestToDoctor(UserRequest userRequest);

    @Mapping(target = "interpretations", ignore = true)
    @Mapping(target = "password", ignore = true)
    Doctor userResponseToDoctor(UserResponse userResponse);

    UserResponse doctorToUserResponse(Doctor doctor);

    List<UserResponse> doctorsToUserResponses(List<Doctor> doctors);
}
