package com.javaoop.movie_booking_app.dto;

import com.javaoop.movie_booking_app.model.TicketType;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

/**
 * Data Transfer Object (DTO) representing the booking form submitted by a user
 * when selecting seats and optionally a ticket type for a specific showtime.
 * <p>
 * This class is used as a carrier of booking-related data between the user interface
 * (view) and the back-end services or controllers in the movie booking application.
 * It encapsulates the list of seat IDs selected by the user and the ticket type
 * they choose (e.g., Adult, Child, Senior).
 * </p>
 * <p>
 * Validation constraints ensure that at least one seat must be selected before
 * submission to prevent empty bookings.
 * </p>
 * 
 * <h2>Usage Example:</h2>
 * <pre>
 * BookingFormDto form = new BookingFormDto();
 * form.setSeatIds(Arrays.asList(1L, 2L, 3L));
 * form.setType(TicketType.ADULT);
 * </pre>
 *
 * @see com.javaoop.movie_booking_app.model.TicketType
 */
public class BookingFormDto {
    /**
     * Creates an empty BookingFormDto instance.
     * This default constructor is required for some frameworks and serialization libraries.
     */
    public BookingFormDto() {
        // default constructor
    }

    /**
     * The list of seat IDs selected by the user for booking.
     * This list must contain at least one seat ID; otherwise,
     * validation will fail and the booking will not proceed.
     */
    @NotEmpty(message = "Please select at least one seat to book.")
    private List<Long> seatIds;

    /**
     * The type of ticket chosen by the user for the booking.
     * This field is optional and can be {@code null} if the user
     * does not select a specific ticket type or if ticket types
     * are not applicable for the booking.
     *
     * @see com.javaoop.movie_booking_app.model.TicketType
     */
    private TicketType type;

    /**
     * Returns the list of seat IDs selected for booking.
     *
     * @return the list of seat IDs; may be empty or {@code null} if not set
     */
    public List<Long> getSeatIds() {
        return seatIds;
    }

    /**
     * Sets the list of seat IDs selected for booking.
     *
     * @param seatIds the list of seat IDs to set; must not be empty to pass validation
     */
    public void setSeatIds(List<Long> seatIds) {
        this.seatIds = seatIds;
    }

    /**
     * Returns the selected ticket type for the booking.
     *
     * @return the ticket type chosen by the user, or {@code null} if none selected
     */
    public TicketType getType() {
        return type;
    }

    /**
     * Sets the ticket type for the booking.
     *
     * @param type the ticket type to set; may be {@code null}
     */
    public void setType(TicketType type) {
        this.type = type;
    }
}
