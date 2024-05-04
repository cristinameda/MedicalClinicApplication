package com.meda.titu.medicalclinicapplication.repository;

import com.meda.titu.medicalclinicapplication.builder.TestDataBuilder;
import com.meda.titu.medicalclinicapplication.entity.ct.CTInterpretation;
import com.meda.titu.medicalclinicapplication.entity.ct.CTScan;
import com.meda.titu.medicalclinicapplication.entity.user.Doctor;
import com.meda.titu.medicalclinicapplication.entity.user.Patient;
import com.meda.titu.medicalclinicapplication.entity.user.Radiologist;
import com.meda.titu.medicalclinicapplication.repository.ct.CTInterpretationRepository;
import com.meda.titu.medicalclinicapplication.repository.ct.CTScanRepository;
import com.meda.titu.medicalclinicapplication.repository.user.DoctorRepository;
import com.meda.titu.medicalclinicapplication.repository.user.PatientRepository;
import com.meda.titu.medicalclinicapplication.repository.user.RadiologistRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class UserRepositoryTest {
    private static final String PATH_CT_SCAN_1 = "src/test/resources/scan/lung.jpg";
    private static final String PATH_CT_SCAN_2 = "src/test/resources/scan/lung2.jpg";
    private static Doctor doctor;
    private static Radiologist radiologist;
    private static Patient patient;
    private static CTScan ctScan;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private RadiologistRepository radiologistRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private CTScanRepository ctScanRepository;
    @Autowired
    private CTInterpretationRepository ctInterpretationRepository;

    @BeforeEach
    public void init() {
        doctor = this.doctorRepository.save(TestDataBuilder.buildDoctor("doctor"));
        radiologist = this.radiologistRepository.save(TestDataBuilder.buildRadiologist("radiologist"));
        patient = this.patientRepository.save(TestDataBuilder.buildPatient("patient"));
        ctScan = this.ctScanRepository.save(
                Objects.requireNonNull(
                        TestDataBuilder.buildCTScan(PATH_CT_SCAN_1)
                ));
    }

    @Test
    public void testOneToMany_oneDoctor_manyCTInterpretations() {
        Radiologist radiologist2 = this.radiologistRepository.save(TestDataBuilder.buildRadiologist("radiologist2"));
        Patient patient2 = this.patientRepository.save(TestDataBuilder.buildPatient("patient2"));
        CTScan ctScan2 = this.ctScanRepository.save(
                Objects.requireNonNull(
                        TestDataBuilder.buildCTScan(PATH_CT_SCAN_2)
                ));
        CTInterpretation ctInterpretation1 = TestDataBuilder.buildCTInterpretation(
                doctor,
                patient,
                radiologist,
                ctScan
        );
        ctInterpretation1 = this.ctInterpretationRepository.save(ctInterpretation1);
        CTInterpretation ctInterpretation2 = TestDataBuilder.buildCTInterpretation(
                doctor,
                patient2,
                radiologist2,
                ctScan2
        );
        ctInterpretation2 = this.ctInterpretationRepository.save(ctInterpretation2);

        verifyInterpretations(ctInterpretation1, ctInterpretation2, ctScan2);
        assertEquals(ctInterpretation1.getDoctor(), ctInterpretation2.getDoctor());
    }

    @Test
    public void testOneToMany_onePatient_manyCTInterpretations() {
        Radiologist radiologist2 = this.radiologistRepository.save(TestDataBuilder.buildRadiologist("radiologist2"));
        Doctor doctor2 = this.doctorRepository.save(TestDataBuilder.buildDoctor("doctor2"));
        CTScan ctScan2 = this.ctScanRepository.save(
                Objects.requireNonNull(
                        TestDataBuilder.buildCTScan(PATH_CT_SCAN_2)
                ));
        CTInterpretation ctInterpretation1 = TestDataBuilder.buildCTInterpretation(
                doctor,
                patient,
                radiologist,
                ctScan
        );
        ctInterpretation1 = this.ctInterpretationRepository.save(ctInterpretation1);
        CTInterpretation ctInterpretation2 = TestDataBuilder.buildCTInterpretation(
                doctor2,
                patient,
                radiologist2,
                ctScan2
        );
        ctInterpretation2 = this.ctInterpretationRepository.save(ctInterpretation2);

        verifyInterpretations(ctInterpretation1, ctInterpretation2, ctScan2);
        assertEquals(ctInterpretation1.getPatient(), ctInterpretation2.getPatient());
    }

    @Test
    public void testOneToMany_oneRadiologist_manyCTInterpretations() {
        Patient patient2 = this.patientRepository.save(TestDataBuilder.buildPatient("patient2"));
        Doctor doctor2 = this.doctorRepository.save(TestDataBuilder.buildDoctor("doctor2"));
        CTScan ctScan2 = this.ctScanRepository.save(
                Objects.requireNonNull(
                        TestDataBuilder.buildCTScan(PATH_CT_SCAN_2)
                ));
        CTInterpretation ctInterpretation1 = TestDataBuilder.buildCTInterpretation(
                doctor,
                patient,
                radiologist,
                ctScan
        );
        ctInterpretation1 = this.ctInterpretationRepository.save(ctInterpretation1);
        CTInterpretation ctInterpretation2 = TestDataBuilder.buildCTInterpretation(
                doctor2,
                patient2,
                radiologist,
                ctScan2
        );
        ctInterpretation2 = this.ctInterpretationRepository.save(ctInterpretation2);

        verifyInterpretations(ctInterpretation1, ctInterpretation2, ctScan2);
        assertEquals(ctInterpretation1.getMadeBy(), ctInterpretation2.getMadeBy());
    }

    @Test
    public void testOneToOne_oneCTScan_oneInterpretation() {
        Doctor doctor2 = this.doctorRepository.save(TestDataBuilder.buildDoctor("doctor2"));
        Radiologist radiologist2 = this.radiologistRepository.save(TestDataBuilder.buildRadiologist("radiologist2"));
        Patient patient2 = this.patientRepository.save(TestDataBuilder.buildPatient("patient2"));
        CTInterpretation ctInterpretation1 = TestDataBuilder.buildCTInterpretation(
                doctor,
                patient,
                radiologist,
                ctScan
        );
        ctInterpretation1 = this.ctInterpretationRepository.save(ctInterpretation1);

        CTInterpretation ctInterpretation2 = TestDataBuilder.buildCTInterpretation(
                doctor2,
                patient2,
                radiologist2,
                ctScan
        );

        assertThrows(DataIntegrityViolationException.class, () -> this.ctInterpretationRepository.saveAndFlush(ctInterpretation2));
    }

    private void verifyInterpretations(CTInterpretation ctInterpretation1, CTInterpretation ctInterpretation2, CTScan ctScan2) {
        assertNotNull(this.ctInterpretationRepository.findById(ctInterpretation1.getCtScan().getId()));
        assertNotNull(ctInterpretation1.getCreatedOn());
        assertEquals(ctScan.getId(), ctInterpretation1.getCtScan().getId());

        assertNotNull(this.ctInterpretationRepository.findById(ctInterpretation2.getCtScan().getId()));
        assertNotNull(ctInterpretation2.getCreatedOn());
        assertEquals(ctScan2.getId(), ctInterpretation2.getCtScan().getId());
    }
}
