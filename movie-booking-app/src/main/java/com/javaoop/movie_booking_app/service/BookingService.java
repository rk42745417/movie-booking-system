package com.javaoop.movie_booking_app.service;

import com.javaoop.movie_booking_app.dto.BookingFormDto;
import com.javaoop.movie_booking_app.exception.SeatUnavailableException;
import com.javaoop.movie_booking_app.model.*;
import com.javaoop.movie_booking_app.repository.BookingRepository;
import com.javaoop.movie_booking_app.repository.MemberRepository;
import com.javaoop.movie_booking_app.repository.SeatRepository;
import com.javaoop.movie_booking_app.repository.ShowtimeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final MemberRepository memberRepository;
    private final ShowtimeRepository showtimeRepository;
    private final SeatRepository seatRepository; // Assuming this exists

    public BookingService(BookingRepository bookingRepository,
                          MemberRepository memberRepository,
                          ShowtimeRepository showtimeRepository,
                          SeatRepository seatRepository) {
        this.bookingRepository = bookingRepository;
        this.memberRepository = memberRepository;
        this.showtimeRepository = showtimeRepository;
        this.seatRepository = seatRepository;
    }

    @Transactional
    public void createBooking(BookingFormDto request, Long memberId, Long showtimeId) throws SeatUnavailableException, EntityNotFoundException, IllegalStateException {
        // Step 1: Check for conflicts BEFORE doing anything else.
        long conflictingBookings = seatRepository.countConflictingBookings(
                showtimeId,
                request.getSeatIds()
        );

        if (conflictingBookings > 0) {
            throw new SeatUnavailableException("包含已被訂位的位置");
        }

        // Step 2: Fetch the required entities from the database.
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("找不到使用者"));

        Showtime showtime = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new EntityNotFoundException("找不到場次"));

        LocalDate validBirthDay = LocalDate.now().minusYears(showtime.getMovie().getRatingCategory().minimumAge());
        if (member.getDateOfBirth().isAfter(validBirthDay)) {
            throw new IllegalStateException("不符合年齡分級");
        }

        List<Seat> seatsList = seatRepository.findAllById(request.getSeatIds());
        if (seatsList.size() != request.getSeatIds().size()) {
            throw new EntityNotFoundException("包含不合法的位置");
        }
        Set<Seat> seatsToBook = Set.copyOf(seatsList);

        // Step 3: Create and save the new Booking object.
        Booking newBooking = new Booking();
        newBooking.setMember(member);
        newBooking.setShowtime(showtime);
        newBooking.setSeats(seatsToBook);
        newBooking.setStatus(BookingStatus.PENDING); // Default status, can be updated after payment
        newBooking.setType(request.getType());

        bookingRepository.save(newBooking);
    }

    @Transactional
    public void cancelBooking(Long bookingId, Long memberId) {
        // 1. Find the booking
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("找不到訂單"));

        // 2. Authorization Check: Does this booking belong to the user?
        if (!booking.getMember().getMemberId().equals(memberId)) {
            throw new IllegalStateException("這不是你的訂單");
        }

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new IllegalStateException("該訂單未處於等待狀態");
        }

        // 4. Business Rule Check: Is the showtime in 30 minutes later?
        if (booking.getShowtime().getStartTime().isBefore(LocalDateTime.now().plusMinutes(30))) {
            throw new IllegalStateException("取消需最遲於 30 分鐘前");
        }

        // 5. All checks passed. Perform the cancellation.
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }

    @Transactional
    public void updateBookingStatus(Long bookingId, BookingStatus status) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalStateException("找不到訂單"));

        booking.setStatus(status);
        bookingRepository.save(booking);
    }

    public List<Booking> findBookingsForMember(Member member) {
        return bookingRepository.findByMemberOrderByShowtimeStartTimeDesc(member);
    }

    public List<Booking> findBookingsForShowtime(Showtime showtime) {
        return bookingRepository.findByShowtimeOrderByShowtimeStartTimeDesc(showtime);
    }

    public List<Booking> findAllBookings() {
        return bookingRepository.findAll();
    }
}