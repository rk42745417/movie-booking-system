package com.javaoop.movie_booking_app.repository;

import com.javaoop.movie_booking_app.model.Hall;
import com.javaoop.movie_booking_app.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Repository interface for managing {@link Seat} entities.
 * <p>
 * Provides methods to retrieve seats by hall, and to check seat availability
 * for specific showtimes by querying existing confirmed bookings.
 * </p>
 */
@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    /**
     * Retrieves all seats for a given hall, ordered by row ascending,
     * then by seat number ascending.
     *
     * @param hall the {@link Hall} entity whose seats to retrieve
     * @return a list of seats in the specified hall, ordered by row and number
     */
    List<Seat> findByHallOrderByRowAscNumberAsc(Hall hall);

    /**
     * Counts the number of existing, confirmed bookings for a given showtime
     * that include any of the specified seats.
     * <p>
     * If this count is greater than zero, it indicates there is a booking conflict
     * for one or more of the requested seats.
     * </p>
     *
     * @param showtimeId the ID of the showtime to check
     * @param seatIds    a list of seat IDs that are requested for booking
     * @return the number of conflicting bookings found for the given showtime and seats
     */
    @Query("SELECT COUNT(b) FROM Booking b JOIN b.seats s " +
           "WHERE b.showtime.showtimeId = :showtimeId " +
           "AND s.seatId IN :seatIds " +
           "AND b.status <> 'CANCELLED'")
    long countConflictingBookings(@Param("showtimeId") Long showtimeId, @Param("seatIds") List<Long> seatIds);

    /**
     * Finds the IDs of all seats that are part of a confirmed booking
     * for a specific showtime.
     * <p>
     * This is typically used to mark seats as unavailable (disabled)
     * in the booking UI to prevent double booking.
     * </p>
     *
     * @param showtimeId the ID of the showtime for which booked seats are queried
     * @return a set of seat IDs that are already booked and confirmed for the showtime
     */
    @Query("SELECT s.seatId FROM Booking b JOIN b.seats s " +
           "WHERE b.showtime.showtimeId = :showtimeId AND b.status <> 'CANCELLED'")
    Set<Long> findBookedSeatIdsByShowtimeId(@Param("showtimeId") Long showtimeId);
}
