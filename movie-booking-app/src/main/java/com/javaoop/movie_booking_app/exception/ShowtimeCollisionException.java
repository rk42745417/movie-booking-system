package com.javaoop.movie_booking_app.exception;

public class ShowtimeCollisionException extends RuntimeException {
    public ShowtimeCollisionException(String message) {
        super(message);
    }
}
