package com.javaoop.gym_booking_app.repository;

import com.javaoop.gym_booking_app.model.Reservation;
import com.javaoop.gym_booking_app.model.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // Find all reservations for a specific member
    List<Reservation> findByMemberId(Long memberId);

    // Find all reservations for a specific course
    List<Reservation> findByCourseId(Long courseId);

    // Count reservations for a specific course to check against capacity
    long countByCourseIdAndStatus(Long courseId, ReservationStatus status);
}