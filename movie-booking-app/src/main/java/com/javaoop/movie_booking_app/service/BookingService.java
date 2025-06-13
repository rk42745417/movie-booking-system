package com.javaoop.movie_booking_app.service;

import com.javaoop.movie_booking_app.dto.BookingFormDto;
import com.javaoop.movie_booking_app.exception.SeatUnavailableException;
import com.javaoop.movie_booking_app.model.*;
import com.javaoop.movie_booking_app.repository.BookingRepository;
import com.javaoop.movie_booking_app.repository.MemberRepository;
import com.javaoop.movie_booking_app.repository.SeatRepository;
import com.javaoop.movie_booking_app.repository.ShowtimeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Service class responsible for managing movie ticket bookings.
 * Provides methods to create, cancel, update booking status, and query bookings.
 */
@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final MemberRepository memberRepository;
    private final ShowtimeRepository showtimeRepository;
    private final SeatRepository seatRepository;

    /**
     * Constructs a BookingService with required repositories.
     *
     * @param bookingRepository the booking repository
     * @param memberRepository the member repository
     * @param showtimeRepository the showtime repository
     * @param seatRepository the seat repository
     */
    public BookingService(BookingRepository bookingRepository,
                          MemberRepository memberRepository,
                          ShowtimeRepository showtimeRepository,
                          SeatRepository seatRepository) {
        this.bookingRepository = bookingRepository;
        this.memberRepository = memberRepository;
        this.showtimeRepository = showtimeRepository;
        this.seatRepository = seatRepository;
    }

    /**
     * Creates a new booking for a given member and showtime based on the booking request.
     * Performs seat availability check, member existence and age verification, and seat validity.
     *
     * @param request the booking form DTO containing seat IDs and booking type
     * @param memberId the ID of the member making the booking
     * @param showtimeId the ID of the showtime to book
     * @throws SeatUnavailableException if any requested seat is already booked
     * @throws EntityNotFoundException if member, showtime, or requested seats do not exist
     * @throws IllegalStateException if the member does not meet the age requirement for the movie rating
     */
    @Transactional
    public void createBooking(BookingFormDto request, Long memberId, Long showtimeId) throws SeatUnavailableException, EntityNotFoundException, IllegalStateException {
        // Check for seat conflicts before proceeding
        long conflictingBookings = seatRepository.countConflictingBookings(
                showtimeId,
                request.getSeatIds()
        );

        if (conflictingBookings > 0) {
            throw new SeatUnavailableException("包含已被訂位的位置");
        }

        // Retrieve member and showtime entities from database
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("找不到使用者"));

        Showtime showtime = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new EntityNotFoundException("找不到場次"));

        // Verify age restriction compliance based on movie rating category
        LocalDate validBirthDay = LocalDate.now().minusYears(showtime.getMovie().getRatingCategory().minimumAge());
        if (member.getDateOfBirth().isAfter(validBirthDay)) {
            throw new IllegalStateException("不符合年齡分級");
        }

        // Validate seats requested exist
        List<Seat> seatsList = seatRepository.findAllById(request.getSeatIds());
        if (seatsList.size() != request.getSeatIds().size()) {
            throw new EntityNotFoundException("包含不合法的位置");
        }
        Set<Seat> seatsToBook = Set.copyOf(seatsList);

        // Create new booking entity and persist it
        Booking newBooking = new Booking();
        newBooking.setMember(member);
        newBooking.setShowtime(showtime);
        newBooking.setSeats(seatsToBook);
        newBooking.setStatus(BookingStatus.PENDING); // Default status before payment
        newBooking.setType(request.getType());

        bookingRepository.save(newBooking);
    }

    /**
     * Cancels an existing booking for a member if allowed.
     * Validates booking ownership, current booking status, and time constraints before cancellation.
     *
     * @param bookingId the ID of the booking to cancel
     * @param memberId the ID of the member requesting cancellation
     * @throws EntityNotFoundException if the booking is not found
     * @throws IllegalStateException if the booking does not belong to the member,
     *                               if the booking status is not PENDING,
     *                               or if cancellation is attempted less than 30 minutes before showtime
     */
    @Transactional
    public void cancelBooking(Long bookingId, Long memberId) {
        // Find booking by ID
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("找不到訂單"));

        // Verify that booking belongs to the member
        if (!booking.getMember().getMemberId().equals(memberId)) {
            throw new IllegalStateException("這不是你的訂單");
        }

        // Only allow cancellation if booking status is PENDING
        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new IllegalStateException("該訂單未處於等待狀態");
        }

        // Check if cancellation is at least 30 minutes prior to showtime start
        if (booking.getShowtime().getStartTime().isBefore(LocalDateTime.now().plusMinutes(30))) {
            throw new IllegalStateException("取消需最遲於 30 分鐘前");
        }

        // Perform cancellation by updating status
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }

    /**
     * Updates the status of an existing booking.
     *
     * @param bookingId the ID of the booking to update
     * @param status the new status to set
     * @throws IllegalStateException if the booking is not found
     */
    @Transactional
    public void updateBookingStatus(Long bookingId, BookingStatus status) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalStateException("找不到訂單"));

        booking.setStatus(status);
        bookingRepository.save(booking);
    }

    /**
     * Retrieves all bookings associated with a specific member, ordered by showtime start time descending.
     *
     * @param member the member whose bookings to retrieve
     * @return list of bookings for the given member
     */
    public List<Booking> findBookingsForMember(Member member) {
        return bookingRepository.findByMemberOrderByShowtimeStartTimeDesc(member);
    }

    /**
     * Retrieves all bookings for a specific showtime, ordered by showtime start time descending.
     *
     * @param showtime the showtime whose bookings to retrieve
     * @return list of bookings for the given showtime
     */
    public List<Booking> findBookingsForShowtime(Showtime showtime) {
        return bookingRepository.findByShowtimeOrderByShowtimeStartTimeDesc(showtime);
    }

    /**
     * Retrieves all bookings in the system.
     *
     * @return list of all bookings
     */
    public List<Booking> findAllBookings() {
        return bookingRepository.findAll();
    }
}
