package com.javaoop.movie_booking_app.model;

/**
 * Enumeration representing the possible statuses of a booking.
 * <ul>
 *     <li>{@link #CONFIRMED} — The booking has been successfully confirmed.</li>
 *     <li>{@link #CANCELLED} — The booking has been cancelled by the user or system.</li>
 *     <li>{@link #PENDING} — The booking is pending and awaiting confirmation or payment.</li>
 * </ul>
 */
public enum BookingStatus {
    /**
     * Booking is confirmed and valid.
     */
    CONFIRMED,

    /**
     * Booking has been cancelled.
     */
    CANCELLED,

    /**
     * Booking is pending confirmation or payment.
     */
    PENDING
}
