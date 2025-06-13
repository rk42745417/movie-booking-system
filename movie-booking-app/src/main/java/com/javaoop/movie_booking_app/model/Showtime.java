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

import java.time.LocalDateTime;

/**
 * Represents a specific movie screening (showtime) in a cinema.
 * Contains references to the movie, hall, and the start/end times of the screening.
 */
@Entity
@Table(name = "Showtimes", indexes = {
        @Index(name = "idx_movie_id", columnList = "movie_id"),
        @Index(name = "idx_hall_id", columnList = "hall_id"),
        @Index(name = "idx_start_time", columnList = "start_time")
})
public class Showtime {
    /**
     * Unique identifier for the showtime.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "showtime_id", nullable = false, updatable = false)
    private Long showtimeId;

    /**
     * The movie being screened at this showtime.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    /**
     * The hall in which the movie is screened.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hall_id", nullable = false)
    private Hall hall;

    /**
     * The start date and time of the movie screening.
     */
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    /**
     * The end date and time of the movie screening, usually calculated as start time plus movie duration.
     */
    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    /**
     * Default constructor for JPA.
     */
    public Showtime() {
    }

    /**
     * Constructs a new Showtime with the specified movie, hall, start time, and end time.
     *
     * @param movie     The movie being shown.
     * @param hall      The hall where the screening takes place.
     * @param startTime The start date and time of the screening.
     * @param endTime   The end date and time of the screening.
     */
    public Showtime(Movie movie, Hall hall, LocalDateTime startTime, LocalDateTime endTime) {
        this.movie = movie;
        this.hall = hall;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Gets the unique showtime ID.
     *
     * @return the showtimeId
     */
    public Long getShowtimeId() {
        return showtimeId;
    }

    /**
     * Sets the unique showtime ID.
     *
     * @param showtimeId the showtimeId to set
     */
    public void setShowtimeId(Long showtimeId) {
        this.showtimeId = showtimeId;
    }

    /**
     * Gets the movie being screened.
     *
     * @return the movie
     */
    public Movie getMovie() {
        return movie;
    }

    /**
     * Sets the movie being screened.
     *
     * @param movie the movie to set
     */
    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    /**
     * Gets the hall where the screening is held.
     *
     * @return the hall
     */
    public Hall getHall() {
        return hall;
    }

    /**
     * Sets the hall where the screening is held.
     *
     * @param hall the hall to set
     */
    public void setHall(Hall hall) {
        this.hall = hall;
    }

    /**
     * Gets the start date and time of the screening.
     *
     * @return the startTime
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Sets the start date and time of the screening.
     *
     * @param startTime the startTime to set
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the end date and time of the screening.
     *
     * @return the endTime
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * Sets the end date and time of the screening.
     *
     * @param endTime the endTime to set
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Returns a string representation of this showtime.
     *
     * @return string with showtime ID, movie ID, hall ID, start time, and end time
     */
    @Override
    public String toString() {
        return "Showtime{" +
                "showtimeId=" + showtimeId +
                ", movieId=" + (movie != null ? movie.getMovieId() : "null") +
                ", hallId=" + (hall != null ? hall.getHallId() : "null") +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
