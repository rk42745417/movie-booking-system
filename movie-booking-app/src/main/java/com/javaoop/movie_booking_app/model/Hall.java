package com.javaoop.movie_booking_app.model;

import jakarta.persistence.*;

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
    @Enumerated(EnumType.STRING)
    @Column(name = "hall_type", nullable = false, length = 20)
    private HallType hallType;

    public Hall() {
    }

    public Hall(HallType hallType) {
        this.hallType = hallType;
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

    public HallType getHallType() {
        return hallType;
    }

    public void setHallType(HallType hallType) {
        this.hallType = hallType;
    }

    @Override
    public String toString() {
        return "Hall{" +
                "hallId=" + hallId +
                // (name != null ? ", name='" + name + '\'' : "") + // if uncommented
                ", hallType='" + hallType + '\'' +
                '}';
    }
}