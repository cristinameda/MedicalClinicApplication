package com.meda.titu.medicalclinicapplication.config;

import com.meda.titu.medicalclinicapplication.entity.user.Admin;
import com.meda.titu.medicalclinicapplication.entity.user.Doctor;
import com.meda.titu.medicalclinicapplication.entity.user.Patient;
import com.meda.titu.medicalclinicapplication.entity.user.Radiologist;
import com.meda.titu.medicalclinicapplication.exception.NotFoundException;
import com.meda.titu.medicalclinicapplication.repository.user.AdminRepository;
import com.meda.titu.medicalclinicapplication.repository.user.DoctorRepository;
import com.meda.titu.medicalclinicapplication.repository.user.PatientRepository;
import com.meda.titu.medicalclinicapplication.repository.user.RadiologistRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
import java.util.Optional;

@Configuration
@AllArgsConstructor
public class AppConfig {

    private final AdminRepository adminRepository;
    private final DoctorRepository doctorRepository;
    private final RadiologistRepository radiologistRepository;
    private final PatientRepository patientRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Optional<Admin> optionalAdmin = adminRepository.findByUsername(username);
            Optional<Doctor> optionalDoctor = doctorRepository.findByUsername(username);
            Optional<Radiologist> optionalRadiologist = radiologistRepository.findByUsername(username);
            Optional<Patient> optionalPatient = patientRepository.findByUsername(username);

            if (optionalAdmin.isPresent()) {
                return optionalAdmin.get();
            }
            if (optionalDoctor.isPresent()) {
                return optionalDoctor.get();
            }
            if (optionalRadiologist.isPresent()) {
                return optionalRadiologist.get();
            }
            if (optionalPatient.isPresent()) {
                return optionalPatient.get();
            }

            throw new NotFoundException("User with username " + username + " could not be found!");
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("https://petstore.swagger.io/*"));
        corsConfiguration.setAllowedMethods(List.of("*"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
