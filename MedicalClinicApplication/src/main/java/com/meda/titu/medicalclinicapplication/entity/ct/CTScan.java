package com.meda.titu.medicalclinicapplication.entity.ct;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "ct_images")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CTScan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private byte[] content;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime scannedOn;
    @OneToOne(mappedBy = "ctScan", cascade = CascadeType.ALL)
    private CTInterpretation ctInterpretation;
}
