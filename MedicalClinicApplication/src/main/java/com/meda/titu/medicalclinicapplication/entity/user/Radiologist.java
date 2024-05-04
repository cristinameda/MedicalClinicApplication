package com.meda.titu.medicalclinicapplication.entity.user;

import com.meda.titu.medicalclinicapplication.entity.ct.CTInterpretation;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "radiologists")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Radiologist extends User implements UserDetails {
    @Transient
    private static final List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_RADIOLOGIST"));

    @OneToMany(mappedBy = "madeBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CTInterpretation> interpretations;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
