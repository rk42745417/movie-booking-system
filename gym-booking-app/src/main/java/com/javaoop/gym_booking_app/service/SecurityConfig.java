package com.javaoop.gym_booking_app.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration for security.
 */
@Configuration
public class SecurityConfig {

    /**
     * Provides a password encoder bean.
     * @return A password encoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the security filter chain.
     * @param http The HttpSecurity to configure.
     * @return The security filter chain.
     * @throws Exception If an error occurs.
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())          // Disable CSRF for now
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll())       // Allow all requests
                .build();
    }
}