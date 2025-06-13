package com.javaoop.gym_booking_app.repository;

import com.javaoop.gym_booking_app.model.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for handling coaches.
 */
@Repository
public interface CoachRepository extends JpaRepository<Coach, Long> {
    /**
     * Finds a coach by their email.
     * @param email The email of the coach to find.
     * @return An Optional containing the coach if found, otherwise empty.
     */
    Optional<Coach> findByEmail(String email);

}