package com.javaoop.movie_booking_app.controller;

import com.javaoop.movie_booking_app.model.*;
import com.javaoop.movie_booking_app.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;
    private final ShowtimeRepository showtimeRepository;
    private final MemberRepository memberRepository;
    private final BookedTicketRepository bookedTicketRepository;

    @Autowired
    public BookingController(
            BookingRepository bookingRepository,
            SeatRepository seatRepository,
            ShowtimeRepository showtimeRepository,
            MemberRepository memberRepository,
            BookedTicketRepository bookedTicketRepository
    ) {
        this.bookingRepository = bookingRepository;
        this.seatRepository = seatRepository;
        this.showtimeRepository = showtimeRepository;
        this.memberRepository = memberRepository;
        this.bookedTicketRepository = bookedTicketRepository;
    }

    /**
     * Create a new booking with selected seats.
     */
    @PostMapping("/create")
    @Transactional
    public ResponseEntity<?> createBooking(
            @RequestParam Long memberId,
            @RequestParam Long showtimeId,
            @RequestBody List<SeatRequest> seatRequests
    ) {
        Optional<Member> memberOpt = memberRepository.findById(memberId);
        Optional<Showtime> showtimeOpt = showtimeRepository.findById(showtimeId);

        if (memberOpt.isEmpty() || showtimeOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid member or showtime ID."));
        }

        Member member = memberOpt.get();
        Showtime showtime = showtimeOpt.get();

        // Check if any seat is already booked for this showtime
        for (SeatRequest seatReq : seatRequests) {
            boolean seatBooked = bookedTicketRepository.existsByBooking_Showtime_ShowtimeIdAndSeat_SeatId(showtimeId, seatReq.getSeatId());
            if (seatBooked) {
                return ResponseEntity.badRequest().body(
                        Map.of("error", "Seat ID " + seatReq.getSeatId() + " is already booked.")
                );
            }
        }

        // Create new booking
        Booking booking = new Booking();
        booking.setMember(member);
        booking.setShowtime(showtime);
        booking.setStatus(BookingStatus.CONFIRMED);
        booking = bookingRepository.save(booking);

        // Create BookedTickets
        List<BookedTicket> ticketsToSave = new ArrayList<>();

        for (SeatRequest seatReq : seatRequests) {
            Seat seat = seatRepository.findById(seatReq.getSeatId())
                    .orElseThrow(() -> new IllegalArgumentException("Seat ID not found: " + seatReq.getSeatId()));

            BookedTicket ticket = new BookedTicket();
            ticket.setBooking(booking);
            ticket.setSeat(seat);
            ticket.setTicketType(
                    (seatReq.getTicketType() != null && !seatReq.getTicketType().isBlank())
                            ? seatReq.getTicketType()
                            : "Adult"
            );

            ticketsToSave.add(ticket);
        }

        bookedTicketRepository.saveAll(ticketsToSave);

        // Return booking and tickets info
        Map<String, Object> response = new HashMap<>();
        response.put("bookingId", booking.getBookingId());
        response.put("status", booking.getStatus());
        response.put("bookedTickets", ticketsToSave.stream().map(t -> Map.of(
                "seatId", t.getSeat().getSeatId(),
                "ticketType", t.getTicketType()
        )).toList());

        return ResponseEntity.ok(response);
    }

    
    /**
     * Cancel a booking by ID (sets status to CANCELLED).
     */
    @PostMapping("/{bookingId}/cancel")
    public ResponseEntity<?> cancelBooking(@PathVariable Long bookingId) {
        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
        if (bookingOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Booking not found."));
        }

        Booking booking = bookingOpt.get();
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            return ResponseEntity.badRequest().body(Map.of("error", "Booking already cancelled."));
        }

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);

        return ResponseEntity.ok(Map.of("status", "CANCELLED"));
    }

    /**
     * Get all bookings.
     */
    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    /**
     * Get booking by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getBooking(@PathVariable Long id) {
        Optional<Booking> bookingOpt = bookingRepository.findById(id);
        if (bookingOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Booking not found."));
        }
        return ResponseEntity.ok(bookingOpt.get());
    }
    

    /**
     * Handles the session selection made by the user before choosing seats.
     * This endpoint stores the selected showtime ID and ticket count in the HTTP session.
     *
     * Endpoint: POST /api/bookings/select-session
     */
    @PostMapping("/select-session")
    public ResponseEntity<?> selectSession(@RequestBody SessionSelectionRequest request, HttpSession session) {
        if (request.getShowtimeId() == null || request.getTickets() <= 0) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid input."));
        }

        session.setAttribute("selectedShowtimeId", request.getShowtimeId());
        session.setAttribute("ticketCount", request.getTickets());

        return ResponseEntity.ok().build();
    }

    // Helper DTO for incoming seat requests
    public static class SeatRequest {
        private Long seatId;
        private String ticketType;

        public Long getSeatId() {
            return seatId;
        }

        public void setSeatId(Long seatId) {
            this.seatId = seatId;
        }

        public String getTicketType() {
            return ticketType;
        }

        public void setTicketType(String ticketType) {
            this.ticketType = ticketType;
        }
    }
    
    
    /**
     * A simple DTO used to receive session selection data from the client.
     * Contains the selected showtime ID and the number of tickets requested, movieId and movieTitle.
     * 
     * This is used when a user clicks "下一步" on the session selection page to 
     * pass their choices to the server.
     */
    public static class SessionSelectionRequest {
        private Long showtimeId;
        private int tickets;
        private Long movieId;
        private String movieTitle;

        // Getters and Setters
        public Long getShowtimeId() {
            return showtimeId;
        }

        public void setShowtimeId(Long showtimeId) {
            this.showtimeId = showtimeId;
        }

        public int getTickets() {
            return tickets;
        }

        public void setTickets(int tickets) {
            this.tickets = tickets;
        }

        public Long getMovieId() {
            return movieId;
        }

        public void setMovieId(Long movieId) {
            this.movieId = movieId;
        }

        public String getMovieTitle() {
            return movieTitle;
        }

        public void setMovieTitle(String movieTitle) {
            this.movieTitle = movieTitle;
        }
    }

}


