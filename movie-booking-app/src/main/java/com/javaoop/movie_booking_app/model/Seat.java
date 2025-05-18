package com.javaoop.movie_booking_app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * Defines every seat within each hall, including its location.
 */
@Entity
@Table(name = "Seats",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_hall_row_number",
                        columnNames = {"hall_id", "row_char", "seat_number_in_row"})
        }
)
public class Seat {
    /**
     * Uid for a seat.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id", nullable = false, updatable = false)
    private Long seatId;

    /**
     * The hall this seat belongs to.
     * This represents the foreign key `hall_id`.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hall_id", nullable = false)
    private Hall hall;

    /**
     * Seat row id (e.g., A, B, C).
     */
    @Column(name = "row_char", nullable = false, length = 1)
    private String rowChar;

    /**
     * Seat number within the row (e.g., 1, 2, 10).
     */
    @Column(name = "seat_number_in_row", nullable = false)
    private Integer seatNumberInRow;

    public Seat() {
    }

    public Seat(Hall hall, String rowChar, Integer seatNumberInRow) {
        this.hall = hall;
        this.rowChar = rowChar;
        this.seatNumberInRow = seatNumberInRow;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public String getRowChar() {
        return rowChar;
    }

    public void setRowChar(String rowChar) {
        this.rowChar = rowChar;
    }

    public Integer getSeatNumberInRow() {
        return seatNumberInRow;
    }

    public void setSeatNumberInRow(Integer seatNumberInRow) {
        this.seatNumberInRow = seatNumberInRow;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "seatId=" + seatId +
                ", hallId=" + (hall != null ? hall.getHallId() : "null") + // Avoid NPE if hall is null
                ", rowChar='" + rowChar + '\'' +
                ", seatNumberInRow=" + seatNumberInRow +
                '}';
    }
}