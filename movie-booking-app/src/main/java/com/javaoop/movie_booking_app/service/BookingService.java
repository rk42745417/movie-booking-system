package com.javaoop.movie_booking_app.service;

import com.javaoop.movie_booking_app.model.BookedTicket;
import com.javaoop.movie_booking_app.model.Booking;
import com.javaoop.movie_booking_app.model.Seat;
import com.javaoop.movie_booking_app.repository.BookedTicketRepository;
import com.javaoop.movie_booking_app.repository.BookingRepository;
import com.javaoop.movie_booking_app.repository.SeatRepository;
import com.javaoop.movie_booking_app.model.BookingStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookedTicketRepository bookedTicketRepository;
    private final SeatRepository seatRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository,
                          BookedTicketRepository bookedTicketRepository,
                          SeatRepository seatRepository) {
        this.bookingRepository = bookingRepository;
        this.bookedTicketRepository = bookedTicketRepository;
        this.seatRepository = seatRepository;
    }

    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    /**
     * Cancels a booking by setting status to CANCELLED (does not delete).
     */
    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking ID not found."));
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }

    /**
     * Checks if all given seat IDs are available for the showtime.
     * A seat is unavailable if it already has a booked ticket under the same showtime.
     */
    public boolean areSeatsAvailable(Long showtimeId, List<Long> seatIds) {
        for (Long seatId : seatIds) {
            boolean isBooked = bookedTicketRepository.existsByBooking_Showtime_ShowtimeIdAndSeat_SeatId(showtimeId, seatId);
            if (isBooked) {
                return false;
            }
        }
        return true;
    }

    /**
     * Adds a single booked ticket linked to a booking.
     */
    public BookedTicket addBookedTicket(Long bookingId, Long seatId, String ticketType) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid booking ID"));
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid seat ID"));

        BookedTicket ticket = new BookedTicket();
        ticket.setBooking(booking);
        ticket.setSeat(seat);
        ticket.setTicketType(ticketType);

        return bookedTicketRepository.save(ticket);
    }

    /**
     * Adds multiple booked tickets in batch for efficiency.
     */
    public void addBookedTickets(Long bookingId, List<BookedTicket> tickets) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid booking ID"));
        for (BookedTicket ticket : tickets) {
            ticket.setBooking(booking);
        }
        bookedTicketRepository.saveAll(tickets);
    }

    /**
     * Retrieves booked tickets by booking ID.
     */
    public List<BookedTicket> getBookedTicketsByBookingId(Long bookingId) {
        return bookedTicketRepository.findByBookingBookingId(bookingId);
    }
}



