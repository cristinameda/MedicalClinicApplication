package com.meda.titu.medicalclinicapplication.service.user.impl;

import com.meda.titu.medicalclinicapplication.dto.request.user.UserRequest;
import com.meda.titu.medicalclinicapplication.dto.response.UserResponse;
import com.meda.titu.medicalclinicapplication.entity.user.Admin;
import com.meda.titu.medicalclinicapplication.exception.NotFoundException;
import com.meda.titu.medicalclinicapplication.mapper.user.AdminMapper;
import com.meda.titu.medicalclinicapplication.repository.user.AdminRepository;
import com.meda.titu.medicalclinicapplication.service.user.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "AdminService")
public class AdminServiceImpl implements UserService {
    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(AdminRepository adminRepository, AdminMapper adminMapper, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.adminMapper = adminMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse save(UserRequest adminRequest) {
        Admin admin = adminMapper.userRequestToAdmin(adminRequest);
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminMapper.adminToUserResponse(adminRepository.save(admin));
    }

    @Override
    public UserResponse findById(long id) {
        return adminRepository.findById(id)
                .map(adminMapper::adminToUserResponse)
                .orElseThrow(() -> new NotFoundException("Admin with id " + id +
                        " could not be found!"));
    }

    @Override
    public UserResponse findByUsername(String username) {
        return adminRepository.findByUsername(username)
                .map(adminMapper::adminToUserResponse)
                .orElseThrow(() -> new NotFoundException("Admin with username " + username +
                        " could not be found!"));
    }

    @Override
    public List<UserResponse> findAll() {
        List<Admin> admins = adminRepository.findAll();
        return adminMapper.adminsToUserResponses(admins);
    }

    @Override
    public List<UserResponse> findAllWithFullNameContaining(String word) {
        List<Admin> admins = adminRepository.findAllByFullNameContainingIgnoreCase(word);
        return adminMapper.adminsToUserResponses(admins);
    }

    @Override
    public void deleteById(long id) {
        adminRepository.deleteById(id);
    }
}
