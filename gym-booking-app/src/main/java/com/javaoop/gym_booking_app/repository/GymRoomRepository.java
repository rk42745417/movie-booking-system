package com.javaoop.gym_booking_app.repository;

import com.javaoop.gym_booking_app.model.GymRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GymRoomRepository extends JpaRepository<GymRoom, Long> {

    // You can add custom finders here if needed
    Optional<GymRoom> findByName(String name);
}