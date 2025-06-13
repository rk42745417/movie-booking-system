package com.javaoop.movie_booking_app.service;

import com.javaoop.movie_booking_app.model.Hall;
import com.javaoop.movie_booking_app.model.HallType;
import com.javaoop.movie_booking_app.model.Seat;
import com.javaoop.movie_booking_app.repository.HallRepository;
import com.javaoop.movie_booking_app.repository.SeatRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class responsible for managing halls and their seats.
 * Provides operations to retrieve halls and create halls with corresponding seats.
 */
@Service
public class HallService {
    private final HallRepository hallRepository;
    private final SeatRepository seatRepository;

    /**
     * A private record to represent the seat count configuration per hall type.
     *
     * @param row the last seat row character (e.g., 'L')
     * @param countPerRow the number of seats per row
     */
    private record SeatCount(Character row, Integer countPerRow) {
    }

    /**
     * Returns the seat count configuration based on the hall type.
     *
     * @param hallType the type of the hall (BIG or SMALL)
     * @return SeatCount containing the last row character and seat count per row
     */
    private SeatCount getSeatCount(HallType hallType) {
        return switch (hallType) {
            case BIG -> new SeatCount('L', 30);
            case SMALL -> new SeatCount('I', 16);
        };
    }

    /**
     * Constructs the HallService with the required repositories.
     *
     * @param hallRepository the repository for hall entities
     * @param seatRepository the repository for seat entities
     */
    @Autowired
    public HallService(HallRepository hallRepository, SeatRepository seatRepository) {
        this.hallRepository = hallRepository;
        this.seatRepository = seatRepository;
    }

    /**
     * Retrieves a list of all halls.
     *
     * @return a list containing all Hall entities
     */
    public List<Hall> getAllHalls() {
        return hallRepository.findAll();
    }

    /**
     * Finds a hall by its ID.
     *
     * @param id the ID of the hall to find
     * @return an Optional containing the Hall if found, otherwise empty
     */
    public Optional<Hall> getHallById(Long id) {
        return hallRepository.findById(id);
    }

    /**
     * Creates a new hall and initializes its seats according to the hall type.
     * For each row and seat number in the hall configuration, creates and saves a Seat entity.
     * The hall entity is saved first to ensure seats are associated correctly.
     *
     * @param hall the Hall entity to create
     */
    @Transactional
    public void createHall(Hall hall) {
        hall = hallRepository.save(hall);

        SeatCount seatCount = getSeatCount(hall.getHallType());
        for (char row = 'A'; row <= seatCount.row; row++) {
            for (int i = 1; i <= seatCount.countPerRow; i++) {
                seatRepository.save(new Seat(hall, row, i));
            }
        }
    }
}
