package com.javaoop.movie_booking_app.exception;

/**
 * Custom exception thrown when a selected seat is no longer available during the booking process.
 * <p>
 * This exception indicates that the seat has already been reserved by another user,
 * resulting in a conflict during the booking attempt.
 * </p>
 */
public class SeatUnavailableException extends RuntimeException {

    /**
     * Constructs a new {@code SeatUnavailableException} with the specified detail message.
     *
     * @param message a descriptive message explaining the seat unavailability
     */
    public SeatUnavailableException(String message) {
        super(message);
    }
}
