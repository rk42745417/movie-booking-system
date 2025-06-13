package com.javaoop.movie_booking_app.model;

import jakarta.persistence.*;

/**
 * Represents a seat within a cinema hall.
 * Each seat is identified by a row (letter) and a seat number within that row.
 */
@Entity
@Table(name = "Seats")
public class Seat {
    /**
     * Unique identifier for the seat.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;

    /**
     * The hall to which this seat belongs.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hall_id", nullable = false)
    private Hall hall;

    /**
     * Row identifier for the seat, typically a letter such as 'A', 'B'.
     */
    @Column(name = "row_identifier", nullable = false, length = 2)
    private Character row;

    /**
     * Number identifier within the row, e.g., 1, 2, 10.
     */
    @Column(name = "number_identifier", nullable = false)
    private Integer number;

    /**
     * Constructs a Seat with specified hall, row, and seat number.
     *
     * @param hall   The hall this seat belongs to.
     * @param row    The row identifier (e.g., 'A').
     * @param number The seat number within the row.
     */
    public Seat(Hall hall, Character row, Integer number) {
        this.hall = hall;
        this.row = row;
        this.number = number;
    }

    /**
     * Default no-args constructor for JPA.
     */
    public Seat() {
    }

    /**
     * Gets the unique seat identifier.
     *
     * @return seatId the seat's ID
     */
    public Long getSeatId() {
        return seatId;
    }

    /**
     * Sets the unique seat identifier.
     *
     * @param seatId the seat's ID to set
     */
    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    /**
     * Gets the hall this seat belongs to.
     *
     * @return hall the hall entity
     */
    public Hall getHall() {
        return hall;
    }

    /**
     * Sets the hall this seat belongs to.
     *
     * @param hall the hall entity to set
     */
    public void setHall(Hall hall) {
        this.hall = hall;
    }

    /**
     * Gets the row identifier of the seat.
     *
     * @return row the row character, e.g., 'A'
     */
    public Character getRow() {
        return row;
    }

    /**
     * Sets the row identifier of the seat.
     *
     * @param row the row character to set, e.g., 'A'
     */
    public void setRow(Character row) {
        this.row = row;
    }

    /**
     * Gets the seat number within the row.
     *
     * @return number the seat number
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * Sets the seat number within the row.
     *
     * @param number the seat number to set
     */
    public void setNumber(Integer number) {
        this.number = number;
    }
}
