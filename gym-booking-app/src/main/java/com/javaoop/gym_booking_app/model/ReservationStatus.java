package com.javaoop.gym_booking_app.model;

/**
 * Represents the status of a course reservation.
 */
public enum ReservationStatus {
    RESERVED,  // The spot is successfully reserved.
    CANCELED,  // The member canceled the reservation.
    ATTENDED,  // The member attended the course.
    NO_SHOW    // The member did not attend the course.
}