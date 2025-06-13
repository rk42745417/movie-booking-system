package com.javaoop.movie_booking_app.exception;

/**
 * Custom exception thrown when a showtime conflict occurs during creation or update.
 * <p>
 * This exception is used to indicate that the desired showtime overlaps with an existing one,
 * preventing scheduling due to a time conflict.
 * </p>
 */
public class ShowtimeCollisionException extends RuntimeException {

    /**
     * Constructs a new {@code ShowtimeCollisionException} with the specified detail message.
     *
     * @param message a descriptive message explaining the showtime conflict
     */
    public ShowtimeCollisionException(String message) {
        super(message);
    }
}
