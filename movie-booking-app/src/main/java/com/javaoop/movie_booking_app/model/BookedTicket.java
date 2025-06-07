package com.javaoop.movie_booking_app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * Stores each individual seat/ticket detail under a booking.
 */
@Entity
@Table(name = "Booked_Tickets", 
       indexes = {
           @Index(name = "idx_booking_id", columnList = "booking_id"),
           @Index(name = "idx_seat_id", columnList = "seat_id")
       },
       uniqueConstraints = {
           @UniqueConstraint(name = "uq_showtime_seat", columnNames = {"booking_id", "seat_id"})
       }
)
public class BookedTicket {

    /**
     * Unique ID for each booked ticket seat record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booked_ticket_id", nullable = false, updatable = false)
    private Long bookedTicketId;

    /**
     * Reference to the Booking this ticket belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    /**
     * Reference to the Seat being booked.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    /**
     * Ticket type (e.g., Adult, Child, Senior).
     */
    @Column(name = "ticket_type", nullable = false, length = 50)
    private String ticketType = "Adult";

    public BookedTicket() {
    }

    public BookedTicket(Booking booking, Seat seat, String ticketType) {
        this.booking = booking;
        this.seat = seat;
        this.ticketType = ticketType;
    }

    // Getters and Setters

    public Long getBookedTicketId() {
        return bookedTicketId;
    }

    public void setBookedTicketId(Long bookedTicketId) {
        this.bookedTicketId = bookedTicketId;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    @Override
    public String toString() {
        return "BookedTicket{" +
                "bookedTicketId=" + bookedTicketId +
                ", bookingId=" + (booking != null ? booking.getBookingId() : "null") +
                ", seatId=" + (seat != null ? seat.getSeatId() : "null") +
                ", ticketType='" + ticketType + '\'' +
                '}';
    }
}
