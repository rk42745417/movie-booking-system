package com.javaoop.gym_booking_app.repository;

import com.javaoop.gym_booking_app.model.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoachRepository extends JpaRepository<Coach, Long> {
    // You can add custom query methods here if needed
    Optional<Coach> findByEmail(String email);

}