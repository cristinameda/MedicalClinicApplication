package com.meda.titu.medicalclinicapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MedicalClinicApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicalClinicApplication.class, args);
    }

}
