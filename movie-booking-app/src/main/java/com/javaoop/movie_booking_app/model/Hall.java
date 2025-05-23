package com.javaoop.movie_booking_app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Stores information about the cinema halls.
 */
@Entity
@Table(name = "Halls")
public class Hall {
    /**
     * Unique identifier for the hall (uid).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hall_id", nullable = false, updatable = false)
    private Long hallId;

    /*
    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;
    */

    /**
     * Type of hall, e.g., Large, Small.
     */
    @Column(name = "hall_type", length = 50)
    private String hallType;

    /**
     * Total number of seats in the hall.
     */
    @Column(name = "total_seats", nullable = false)
    private Integer totalSeats;

    public Hall() {
    }

    public Hall(String hallType, Integer totalSeats) {
        this.hallType = hallType;
        this.totalSeats = totalSeats;
    }

    public Long getHallId() {
        return hallId;
    }

    public void setHallId(Long hallId) {
        this.hallId = hallId;
    }

    /*
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    */

    public String getHallType() {
        return hallType;
    }

    public void setHallType(String hallType) {
        this.hallType = hallType;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    @Override
    public String toString() {
        return "Hall{" +
                "hallId=" + hallId +
                // (name != null ? ", name='" + name + '\'' : "") + // if uncommented
                ", hallType='" + hallType + '\'' +
                ", totalSeats=" + totalSeats +
                '}';
    }
}