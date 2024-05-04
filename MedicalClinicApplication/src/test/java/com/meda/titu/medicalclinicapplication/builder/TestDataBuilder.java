package com.meda.titu.medicalclinicapplication.builder;

import com.meda.titu.medicalclinicapplication.entity.ct.CTInterpretation;
import com.meda.titu.medicalclinicapplication.entity.ct.CTScan;
import com.meda.titu.medicalclinicapplication.entity.user.Admin;
import com.meda.titu.medicalclinicapplication.entity.user.Doctor;
import com.meda.titu.medicalclinicapplication.entity.user.Patient;
import com.meda.titu.medicalclinicapplication.entity.user.Radiologist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import static java.nio.file.Files.readAllBytes;

public class TestDataBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestDataBuilder.class);

    public static Admin buildAdmin(String name) {
        return Admin.builder()
                .username(name)
                .password("pass")
                .email(name + "@gmail.com")
                .fullName(name)
                .dob("1990-03-05")
                .phoneNo(generateRandomRomanianPhoneNumber())
                .build();
    }

    public static Patient buildPatient(String name) {
        return Patient.builder()
                .username(name)
                .password("pass")
                .email(name + "@gmail.com")
                .fullName(name)
                .dob("1990-03-05")
                .phoneNo(generateRandomRomanianPhoneNumber())
                .build();
    }

    public static Radiologist buildRadiologist(String name) {
        return Radiologist.builder()
                .username(name)
                .password("pass")
                .email(name + "@gmail.com")
                .fullName(name)
                .dob("1990-03-05")
                .phoneNo(generateRandomRomanianPhoneNumber())
                .build();
    }

    public static Doctor buildDoctor(String name) {
        return Doctor.builder()
                .username(name)
                .password("pass")
                .email(name + "@gmail.com")
                .fullName(name)
                .dob("1990-03-05")
                .phoneNo(generateRandomRomanianPhoneNumber())
                .build();
    }

    public static CTScan buildCTScan(String relativePath) {
        Path path = Paths.get(relativePath);
        String fileName = String.valueOf(path.getFileName());
        String fileType = getFileType(fileName);

        try {
            byte[] content = readAllBytes(path);
            return CTScan.builder()
                    .name(fileName)
                    .content(content)
                    .type(fileType)
                    .build();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    private static String getFileType(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1 || dotIndex == fileName.length() - 1) {
            return ""; // No extension found
        }
        return fileName.substring(dotIndex + 1);
    }

    public static CTInterpretation buildCTInterpretation(
            Doctor doctor,
            Patient patient,
            Radiologist radiologist,
            CTScan ctScan) {
        return CTInterpretation.builder()
                .doctor(doctor)
                .patient(patient)
                .madeBy(radiologist)
                .ctScan(ctScan)
                .prediction("Prediction")
                .interpretation("Interpretation")
                .build();
    }

    private static String generateRandomRomanianPhoneNumber() {
        Random random = new Random();
        StringBuilder phoneNumber = new StringBuilder("+40");

        phoneNumber.append("7");
        phoneNumber.append(random.nextInt(6) + 1);
        for (int i = 0; i < 8; i++) {
            phoneNumber.append(random.nextInt(10));
        }

        return phoneNumber.toString();
    }
}
