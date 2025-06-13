package com.javaoop.movie_booking_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security configuration class for the Movie Booking Application.
 * <p>
 * Sets up URL authorization rules, form login, and password encryption.
 * </p>
 * <ul>
 *     <li>Allows public access to static assets and user registration/login pages</li>
 *     <li>Temporarily allows access to admin pages (no authentication yet)</li>
 *     <li>All other endpoints require authentication</li>
 * </ul>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	/**
	 * Default no-argument constructor.
	 */
	public SecurityConfig() {
	    super();
	}
    /**
     * Defines a {@link PasswordEncoder} bean using BCrypt hashing algorithm.
     *
     * @return a {@link BCryptPasswordEncoder} instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the security filter chain for HTTP requests.
     *
     * It sets up:
     * <ul>
     *     <li>Public access to static resources and user login/register pages</li>
     *     <li>Temporary access to admin pages (since authentication is not fully implemented)</li>
     *     <li>Custom login page at {@code /user/login}</li>
     *     <li>Redirect to home page upon successful login</li>
     *     <li>Logout behavior that redirects to login page with a logout message</li>
     *     <li>All other endpoints require authentication</li>
     * </ul>
     *
     * @param http the {@link HttpSecurity} instance to configure
     * @return a configured {@link SecurityFilterChain}
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                // Allow static resources
                .requestMatchers("/css/**", "/js/**", "/images/**", "/fonts/**", "/favicon.ico").permitAll()
                // Allow global pages
                .requestMatchers("/user/login", "/user/register").permitAll()
                // Allow admin pages (no authentication logic yet)
                .requestMatchers("/admin/**", "/admin").permitAll()
                // Allow error pages (for development/testing)
                .requestMatchers("/error", "/error/**").permitAll()
                // All other requests require authentication
                .anyRequest().authenticated()
        ).formLogin(form -> form
                // Custom login page
                .loginPage("/user/login")
                // Redirect to home page on success
                .defaultSuccessUrl("/", true)
                .permitAll()
        ).logout(logout -> logout
                // Logout URL
                .logoutUrl("/user/logout")
                // Redirect to login page after logout
                .logoutSuccessUrl("/user/login?logout")
                .permitAll()
        );
        return http.build();
    }
}
