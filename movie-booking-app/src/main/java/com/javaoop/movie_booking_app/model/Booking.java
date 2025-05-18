/**
 * 
 */
package com.javaoop.movie_booking_app.model;

/**
 * 
 */
public class Booking {
    private int id;
    private int userId;
    private int showtimeId;
    private String seatLabel;

    public Booking(int id, int userId, int showtimeId, String seatLabel) {
        this.id = id;
        this.userId = userId;
        this.showtimeId = showtimeId;
        this.seatLabel = seatLabel;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public int getShowtimeId() { return showtimeId; }
    public String getSeatLabel() { return seatLabel; }

	/**
	 * @return
	 */
	public int getSessionId() {
		return 0;
	}
}
