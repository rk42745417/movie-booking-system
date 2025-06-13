package com.javaoop.gym_booking_app.model;

/**
 * Represents the status of a course.
 */
public enum CourseStatus {
    OPEN,      // Available for booking
    CLOSED,    // Fully booked or no longer available
    CANCELED   // The course has been canceled
}