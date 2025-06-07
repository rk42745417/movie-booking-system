package com.javaoop.movie_booking_app.repository;

import com.javaoop.movie_booking_app.model.Seat;
import com.javaoop.movie_booking_app.model.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    // Find all seats in a specific hall
    List<Seat> findByHall(Hall hall);

    // Optionally: Find by hall ID
    List<Seat> findByHall_HallId(Long hallId);

    // Optional: Find seat by seat number and hall
    Seat findBySeatIdAndHall(Long seatId, Hall hall);

    // Optional: Find multiple seats by ID list
    List<Seat> findBySeatIdIn(List<Long> seatIds);
}
