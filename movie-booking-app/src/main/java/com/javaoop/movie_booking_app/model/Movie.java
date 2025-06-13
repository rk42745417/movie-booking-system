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
 * Represents a movie entity in the system.
 * <p>
 * Contains essential details such as title, duration, synopsis, rating, poster image, and availability status.
 * </p>
 */
@Entity
@Table(name = "Movies", indexes = {
        @Index(name = "idx_title", columnList = "title")
})
public class Movie {
    /**
     * Unique identifier for the movie.
     * Acts as the primary key in the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id", nullable = false, updatable = false)
    private Long movieId;

    /**
     * The localized title of the movie.
     */
    @Column(name = "title", nullable = false)
    private String title;

    /**
     * The English title of the movie.
     */
    @Column(name = "en_title", nullable = false)
    private String enTitle;

    /**
     * Filename or path for the movie poster image.
     */
    @Column(name = "poster", nullable = false)
    private String poster;

    /**
     * A detailed synopsis or description of the movie.
     * Stored as a large object (TEXT/CLOB).
     */
    @Lob
    @Column(name = "synopsis", columnDefinition = "TEXT")
    private String synopsis;

    /**
     * Duration of the movie in minutes.
     */
    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    /**
     * Movie rating category, used for enforcing age restrictions.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "rating_category", nullable = false)
    private RatingCategory ratingCategory;

    /*
    @Column(name = "release_date")
    private LocalDate releaseDate;
    */

    /**
     * Flag indicating whether the movie is currently active and available for booking.
     * Defaults to true.
     */
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    /**
     * Default no-argument constructor required by JPA.
     */
    public Movie() {
    }

    /**
     * Constructs a new Movie with the specified details.
     *
     * @param title           the localized title of the movie
     * @param enTitle         the English title of the movie
     * @param poster          the filename or path of the movie poster
     * @param synopsis        the synopsis or description of the movie
     * @param durationMinutes the duration of the movie in minutes
     * @param ratingCategory  the rating category of the movie
     */
    public Movie(String title, String enTitle, String poster, String synopsis, Integer durationMinutes, RatingCategory ratingCategory) {
        this.title = title;
        this.enTitle = enTitle;
        this.poster = poster;
        this.synopsis = synopsis;
        this.durationMinutes = durationMinutes;
        this.ratingCategory = ratingCategory;
    }

    /**
     * Returns the unique movie ID.
     *
     * @return the movie ID
     */
    public Long getMovieId() {
        return movieId;
    }

    /**
     * Sets the unique movie ID.
     * Usually managed by JPA and not set manually.
     *
     * @param movieId the movie ID to set
     */
    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    /**
     * Returns the localized title of the movie.
     *
     * @return the movie title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the localized title of the movie.
     *
     * @param title the movie title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the English title of the movie.
     *
     * @return the English title
     */
    public String getEnTitle() {
        return enTitle;
    }

    /**
     * Sets the English title of the movie.
     *
     * @param enTitle the English title to set
     */
    public void setEnTitle(String enTitle) {
        this.enTitle = enTitle;
    }

    /**
     * Returns the poster filename or path.
     *
     * @return the poster image path
     */
    public String getPoster() {
        return poster;
    }

    /**
     * Sets the poster filename or path.
     *
     * @param poster the poster image path to set
     */
    public void setPoster(String poster) {
        this.poster = poster;
    }

    /**
     * Returns the movie synopsis or description.
     *
     * @return the synopsis text
     */
    public String getSynopsis() {
        return synopsis;
    }

    /**
     * Sets the movie synopsis or description.
     *
     * @param synopsis the synopsis text to set
     */
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    /**
     * Returns the duration of the movie in minutes.
     *
     * @return duration in minutes
     */
    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    /**
     * Sets the duration of the movie in minutes.
     *
     * @param durationMinutes the duration in minutes to set
     */
    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    /**
     * Returns the rating category of the movie.
     *
     * @return the rating category
     */
    public RatingCategory getRatingCategory() {
        return ratingCategory;
    }

    /**
     * Sets the rating category of the movie.
     *
     * @param ratingCategory the rating category to set
     */
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
    */

    /**
     * Returns whether the movie is active and available.
     *
     * @return true if active, false otherwise
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Sets the movie's active status.
     *
     * @param active true to mark the movie as active, false to deactivate
     */
    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     * Returns a string representation of the movie object.
     *
     * @return string summarizing key movie details
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
