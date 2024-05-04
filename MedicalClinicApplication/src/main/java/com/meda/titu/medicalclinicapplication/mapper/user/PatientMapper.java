package com.meda.titu.medicalclinicapplication.mapper.user;

import com.meda.titu.medicalclinicapplication.dto.request.user.UserRequest;
import com.meda.titu.medicalclinicapplication.dto.response.UserResponse;
import com.meda.titu.medicalclinicapplication.entity.user.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface PatientMapper {
    @Mapping(target = "interpretations", ignore = true)
    @Mapping(target = "id", ignore = true)
    Patient userRequestToPatient(UserRequest userRequest);

    UserResponse patientToUserResponse(Patient patient);
    @Mapping(target = "interpretations", ignore = true)

    Patient userResponseToPatient(UserResponse userResponse);

    List<UserResponse> patientsToUserResponses(List<Patient> patients);


}
