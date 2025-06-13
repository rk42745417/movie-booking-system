package com.javaoop.movie_booking_app.service;

import com.javaoop.movie_booking_app.model.Hall;
import com.javaoop.movie_booking_app.model.Seat;
import com.javaoop.movie_booking_app.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing seat-related operations such as retrieval and conflict checking.
 */
@Service
public class SeatService {
    private final SeatRepository seatRepository;

    /**
     * Constructs a SeatService with the provided SeatRepository.
     *
     * @param seatRepository repository for accessing seat data
     */
    @Autowired
    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    /**
     * Retrieves a seat by its unique ID.
     *
     * @param id the ID of the seat
     * @return an Optional containing the Seat if found, otherwise empty
     */
    public Optional<Seat> getSeatById(Long id) {
        return seatRepository.findById(id);
    }

    /**
     * Retrieves all seats in a given hall, ordered by row (ascending) and seat number (ascending).
     *
     * @param hall the Hall entity to retrieve seats for
     * @return a list of Seat entities ordered by row and number
     */
    public List<Seat> getAllSeatInHall(Hall hall) {
        return seatRepository.findByHallOrderByRowAscNumberAsc(hall);
    }

    /**
     * Counts how many seats in the given list are already booked for the specified showtime.
     *
     * @param showtimeId the ID of the showtime to check against
     * @param seatIds    a list of seat IDs to check for conflicts
     * @return the number of seats that are already booked (conflicting)
     */
    public long countConflictSeats(Long showtimeId, List<Long> seatIds) {
        return seatRepository.countConflictingBookings(showtimeId, seatIds);
    }
}
