package com.meda.titu.medicalclinicapplication.mapper.user;

import com.meda.titu.medicalclinicapplication.dto.request.user.UserRequest;
import com.meda.titu.medicalclinicapplication.dto.response.UserResponse;
import com.meda.titu.medicalclinicapplication.entity.user.Radiologist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface RadiologistMapper {
    @Mapping(target = "interpretations", ignore = true)
    @Mapping(target = "id", ignore = true)
    Radiologist userRequestToRadiologist(UserRequest userRequest);

    UserResponse radiologistToUserResponse(Radiologist radiologist);
    @Mapping(target = "interpretations", ignore = true)
    Radiologist userResponseToRadiologist(UserResponse userResponse);

    List<UserResponse> radiologistsToUserResponses(List<Radiologist> radiologist);
}
