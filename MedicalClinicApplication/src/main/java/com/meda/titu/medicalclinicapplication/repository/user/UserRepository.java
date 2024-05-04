package com.meda.titu.medicalclinicapplication.repository.user;

import com.meda.titu.medicalclinicapplication.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface UserRepository<T extends User> extends JpaRepository<T, Long> {
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByPhoneNo(String phoneNo);

    Optional<T> findByUsername(String username);

    List<T> findAllByFullNameContainingIgnoreCase(String word);
}
