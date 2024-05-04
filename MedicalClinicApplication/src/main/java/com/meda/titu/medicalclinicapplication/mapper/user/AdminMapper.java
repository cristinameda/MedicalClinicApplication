package com.meda.titu.medicalclinicapplication.mapper.user;

import com.meda.titu.medicalclinicapplication.dto.request.user.UserRequest;
import com.meda.titu.medicalclinicapplication.dto.response.UserResponse;
import com.meda.titu.medicalclinicapplication.entity.user.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface AdminMapper {
    @Mapping(target = "id", ignore = true)
    Admin userRequestToAdmin(UserRequest userRequest);

    UserResponse adminToUserResponse(Admin admin);

    List<UserResponse> adminsToUserResponses(List<Admin> admins);
}
