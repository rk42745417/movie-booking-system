package com.javaoop.gym_booking_app.repository;

import com.javaoop.gym_booking_app.model.GymRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for handling gym rooms.
 */
@Repository
public interface GymRoomRepository extends JpaRepository<GymRoom, Long> {

    /**
     * Finds a gym room by its name.
     * @param name The name of the gym room to find.
     * @return An Optional containing the gym room if found, otherwise empty.
     */
    Optional<GymRoom> findByName(String name);
}