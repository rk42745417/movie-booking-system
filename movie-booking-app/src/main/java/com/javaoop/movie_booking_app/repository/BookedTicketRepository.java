package com.javaoop.movie_booking_app.repository;

import com.javaoop.movie_booking_app.model.BookedTicket;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookedTicketRepository extends JpaRepository<BookedTicket, Long> {

	boolean existsByBooking_Showtime_ShowtimeIdAndSeat_SeatId(Long showtimeId, Long seatId);
	List<BookedTicket> findByBookingBookingId(Long bookingId);

    // You can add custom query methods here if needed, e.g.:
    // List<BookedTicket> findByBookingBookingId(Long bookingId);
	
}


