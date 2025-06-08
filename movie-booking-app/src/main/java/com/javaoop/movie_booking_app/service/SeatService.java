package com.javaoop.movie_booking_app.service;

import com.javaoop.movie_booking_app.model.Hall;
import com.javaoop.movie_booking_app.model.Movie;
import com.javaoop.movie_booking_app.model.Seat;
import com.javaoop.movie_booking_app.repository.MovieRepository;
import com.javaoop.movie_booking_app.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SeatService {
    private final SeatRepository seatRepository;

    @Autowired
    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public Optional<Seat> getSeatById(Long id) {
        return seatRepository.findById(id);
    }

    public List<Seat> getAllSeatInHall(Hall hall) {
        return seatRepository.findByHallOrderByRowAscNumberAsc(hall);
    }

    public long countConflictSeats(Long showtimeId, List<Long> seatIds) {
        return seatRepository.countConflictingBookings(showtimeId, seatIds);
    }
}
