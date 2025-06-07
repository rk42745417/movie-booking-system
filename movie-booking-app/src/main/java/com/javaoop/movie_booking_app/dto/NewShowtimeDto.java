package com.javaoop.movie_booking_app.dto;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

public class NewShowtimeDto {
    @NotEmpty
    private Long hallId;

    private LocalDateTime startTime;

    public void setHallId(Long hallId) {
        this.hallId = hallId;
    }

    public Long getHallId() {
        return hallId;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
}
