package com.javaoop.gym_booking_app.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration

public class SecurityConfig {

    /** 提供給 MemberService 注入 */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /** 若暫時不想出現 Spring Security 的登入畫面，可把所有請求放行 */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())          // 先關掉 CSRF
                   .authorizeHttpRequests(auth -> auth
                         .anyRequest().permitAll())       // 全部放行
                   .build();
    }
}