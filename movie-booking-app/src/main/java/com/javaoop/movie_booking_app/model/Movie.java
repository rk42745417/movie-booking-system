package com.javaoop.movie_booking_app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

/**
 * Stores details about each movie.
 */
@Entity
@Table(name = "Movies", indexes = {
        @Index(name = "idx_title", columnList = "title")
})
public class Movie {
    /**
     * Unique identifier for the movie (uid).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id", nullable = false, updatable = false)
    private Long movieId;

    /**
     * Name of the movie.
     */
    @Column(name = "title", nullable = false)
    private String title;

    /**
     * Brief description of the movie.
     */
    @Lob // For TEXT or CLOB types, indicates a large object
    @Column(name = "synopsis", columnDefinition = "TEXT")
    private String synopsis;

    /**
     * Length of the movie in minutes.
     */
    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    /**
     * Movie rating. Used for age checks.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "rating_category", nullable = false)
    private RatingCategory ratingCategory;

    /*
    @Column(name = "release_date")
    private LocalDate releaseDate;
    */

    /**
     * Indicates if the movieis currently available.
     */
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    public Movie() {
    }

    public Movie(String title, String synopsis, Integer durationMinutes, RatingCategory ratingCategory) {
        this.title = title;
        this.synopsis = synopsis;
        this.durationMinutes = durationMinutes;
        this.ratingCategory = ratingCategory;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public RatingCategory getRatingCategory() {
        return ratingCategory;
    }

    public void setRatingCategory(RatingCategory ratingCategory) {
        this.ratingCategory = ratingCategory;
    }

    /*
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
    */

    @Override
    public String toString() {
        return "Movie{" +
                "movieId=" + movieId +
                ", title='" + title + '\'' +
                ", durationMinutes=" + durationMinutes +
                ", ratingCategory=" + ratingCategory +
                // ", releaseDate=" + releaseDate +
                // ", isActive=" + isActive +
                '}';
    }
}