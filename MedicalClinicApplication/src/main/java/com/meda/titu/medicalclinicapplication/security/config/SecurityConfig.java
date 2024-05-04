package com.meda.titu.medicalclinicapplication.security.config;

import com.meda.titu.medicalclinicapplication.config.AppConfig;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final AppConfig appConfig;
    private final JwtAuthenticationFilter jwtAuthFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(appConfig.corsConfigurationSource()))
                .authorizeHttpRequests(request -> {
                    request.requestMatchers("/auth/**").permitAll();
                    request.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                    request.requestMatchers("/admins/**", "/doctors/**", "/radiologists/**", "/doctors/**").hasAnyAuthority("ROLE_ADMIN");
                    request.anyRequest().authenticated();
                })
                .authenticationProvider(appConfig.authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}
