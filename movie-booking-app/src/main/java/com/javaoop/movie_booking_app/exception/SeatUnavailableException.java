package com.javaoop.movie_booking_app.exception;

// A custom exception for booking conflicts
public class SeatUnavailableException extends RuntimeException {
    public SeatUnavailableException(String message) {
        super(message);
    }
}