package com.javaoop.movie_booking_app.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Showtime {
    private int id;
    private int movieId;
    private int theaterId;
    private LocalDateTime startTime;

    // Constructor
    public Showtime(int id, int movieId, int theaterId, LocalDateTime startTime) {
        this.id = id;
        this.movieId = movieId;
        this.theaterId = theaterId;
        this.startTime = startTime;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getMovieId() {
        return movieId;
    }

    public int getTheaterId() {
        return theaterId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    // Setter for id
    public void setId(int id) {
        this.id = id;
    }

    // Optional: Provide orElse method to handle null cases (or return default value)
    public Showtime orElse(Showtime other) {
        return this == null ? other : this;
    }

    // Override toString for easier debugging
    @Override
    public String toString() {
        return "Showtime{id=" + id + ", movieId=" + movieId + ", theaterId=" + theaterId + ", startTime=" + startTime + "}";
    }

    // Optionally override equals() and hashCode() if comparing Showtime objects is required
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Showtime showtime = (Showtime) obj;
        return id == showtime.id &&
               movieId == showtime.movieId &&
               theaterId == showtime.theaterId &&
               startTime.equals(showtime.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, movieId, theaterId, startTime);
    }
}
