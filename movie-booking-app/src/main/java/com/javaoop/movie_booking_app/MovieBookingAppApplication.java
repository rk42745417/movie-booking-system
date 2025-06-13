package com.javaoop.movie_booking_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point for the Movie Booking Application.
 * <p>
 * This is the main class that bootstraps the Spring Boot application.
 * It initializes the application context and starts the embedded server.
 * </p>
 *
 */
@SpringBootApplication
public class MovieBookingAppApplication {

    /**
     * Default no-argument constructor.
     */
    public MovieBookingAppApplication() {
        // no-op
    }
    
    /**
     * The main method that starts the Movie Booking Application.
     *
     * @param args command-line arguments passed during application startup
     */
    public static void main(String[] args) {
        SpringApplication.run(MovieBookingAppApplication.class, args);
    }

}
