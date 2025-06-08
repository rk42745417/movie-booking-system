package com.javaoop.movie_booking_app.repository;

import com.javaoop.movie_booking_app.model.Hall;
import com.javaoop.movie_booking_app.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByHallOrderByRowAscNumberAsc(Hall hall);

    /**
     * Counts the number of existing, confirmed bookings for a given showtime
     * that include any of the specified seats.
     * If this count is > 0, it means there is a booking conflict.
     *
     * @param showtimeId The ID of the showtime to check.
     * @param seatIds    A list of seat IDs the user wants to book.
     * @return The number of conflicting bookings found.
     */
    @Query("SELECT COUNT(b) FROM Booking b JOIN b.seats s " +
            "WHERE b.showtime.showtimeId = :showtimeId " +
            "AND s.seatId IN :seatIds " +
            "AND b.status <> 'CANCELLED'")
    long countConflictingBookings(@Param("showtimeId") Long showtimeId, @Param("seatIds") List<Long> seatIds);

    /**
     * Finds the IDs of all seats that are part of a confirmed booking for a specific showtime.
     * This is used to disable already-booked seats on the UI.
     */
    @Query("SELECT s.seatId FROM Booking b JOIN b.seats s " +
            "WHERE b.showtime.showtimeId = :showtimeId AND b.status <> 'CANCELLED'")
    Set<Long> findBookedSeatIdsByShowtimeId(@Param("showtimeId") Long showtimeId);
}



