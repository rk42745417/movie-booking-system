package com.javaoop.movie_booking_app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Seats")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hall_id", nullable = false)
    private Hall hall;

    @Column(name = "row_identifier", nullable = false, length = 2)
    private Character row; // e.g., "A", "B"

    @Column(name = "number_identifier", nullable = false)
    private Integer number; // e.g., 1, 2, 10

    public Seat(Hall hall, Character row, Integer number) {
        this.hall = hall;
        this.row = row;
        this.number = number;
    }

    public Seat() {

    }

    public void setRow(Character row) {
        this.row = row;
    }

    public Integer getNumber() {
        return number;
    }

    public Hall getHall() {
        return hall;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public Character getRow() {
        return row;
    }
}
