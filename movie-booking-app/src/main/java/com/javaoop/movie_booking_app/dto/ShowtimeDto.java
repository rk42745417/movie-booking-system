package com.javaoop.movie_booking_app.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for transferring showtime data.
 * <p>
 * Contains the identifier of the hall and the start time of the showtime.
 * </p>
 * <p>
 * This class is mainly used for receiving and sending showtime-related data
 * between client and server.
 * </p>
 */
public class ShowtimeDto {
	

    /**
     * Default no-argument constructor.
     */
    public ShowtimeDto() {
        // No special initialization needed
    }

    /**
     * Unique identifier of the hall.
     * This field must not be null.
     */
    @NotNull(message = "Hall ID must not be null")
    private Long hallId;

    /**
     * The start date and time of the showtime.
     * This field must not be null.
     */
    @NotNull(message = "Start time must not be null")
    private LocalDateTime startTime;

    /**
     * Sets the hall ID.
     *
     * @param hallId the unique identifier of the hall
     */
    public void setHallId(Long hallId) {
        this.hallId = hallId;
    }

    /**
     * Returns the hall ID.
     *
     * @return the unique identifier of the hall
     */
    public Long getHallId() {
        return hallId;
    }

    /**
     * Sets the start time of the showtime.
     *
     * @param startTime the start date and time of the showtime
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Returns the start time of the showtime.
     *
     * @return the start date and time of the showtime
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }
}
