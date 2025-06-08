package com.javaoop.gym_booking_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication   // 會自動掃描「同層與以下」所有 @Component
public class GymBookingAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(GymBookingAppApplication.class, args);
    }
}