package com.javaoop.gym_booking_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class.
 */
@SpringBootApplication   // Automatically scans all @Component in the same layer and below
public class GymBookingAppApplication {

    /**
     * Main method.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(GymBookingAppApplication.class, args);
    }
}