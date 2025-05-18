/**
 * 
 */
package com.javaoop.movie_booking_app.model;

/**
 * 
 */
public class Seat {
    private int theaterId;
    private String seatLabel;
    private boolean isBooked;

    public Seat(int theaterId, String seatLabel, boolean isBooked) {
        this.theaterId = theaterId;
        this.seatLabel = seatLabel;
        this.isBooked = isBooked;
    }

    public int getTheaterId() { return theaterId; }
    public String getSeatLabel() { return seatLabel; }
    public boolean isBooked() { return isBooked; }
}
