package com.javaoop.gym_booking_app.repository;

import com.javaoop.gym_booking_app.model.Reservation;
import com.javaoop.gym_booking_app.model.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for handling reservations.
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    /**
     * Finds all reservations for a specific member.
     * @param memberId The ID of the member.
     * @return A list of all reservations for the specified member.
     */
    List<Reservation> findByMemberId(Long memberId);

    /**
     * Finds all reservations for a specific course.
     * @param courseId The ID of the course.
     * @return A list of all reservations for the specified course.
     */
    List<Reservation> findByCourseId(Long courseId);

    /**
     * Counts the number of reservations for a specific course.
     * @param courseId The ID of the course.
     * @return The number of reservations for the specified course.
     */
    int countByCourseId(Long courseId);

    /**
     * Counts the number of reservations for a specific course with a specific status.
     * @param courseId The ID of the course.
     * @param status The status of the reservations to count.
     * @return The number of reservations for the specified course with the specified status.
     */
    long countByCourseIdAndStatus(Long courseId, ReservationStatus status);
}