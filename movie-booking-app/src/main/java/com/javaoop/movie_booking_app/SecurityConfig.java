package com.javaoop.movie_booking_app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                // Allow static resources
                .requestMatchers("/css/**", "/js/**", "/images/**", "/fonts/**", "/favicon.ico").permitAll()
                // Allow global pages
                .requestMatchers("/user/login", "/user/register").permitAll()
                // Deny others
                .anyRequest().authenticated()
        ).formLogin(form -> form
                .loginPage("/user/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
        ).logout(logout -> logout
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
        );
        return http.build();
    }
}
