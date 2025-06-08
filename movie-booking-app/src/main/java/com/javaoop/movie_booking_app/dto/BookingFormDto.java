package com.javaoop.movie_booking_app.dto;

import com.javaoop.movie_booking_app.model.TicketType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

// Using jakarta.validation for Spring Boot 3+
public class BookingFormDto {
    @NotEmpty(message = "Please select at least one seat to book.")
    private List<Long> seatIds;

    private TicketType type;

    public List<Long> getSeatIds() {
        return seatIds;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public void setSeatIds(List<Long> seatIds) {
        this.seatIds = seatIds;
    }
}