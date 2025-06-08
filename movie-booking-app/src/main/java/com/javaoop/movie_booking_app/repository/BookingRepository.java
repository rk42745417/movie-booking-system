package com.javaoop.movie_booking_app.repository;

import com.javaoop.movie_booking_app.model.Booking;
import com.javaoop.movie_booking_app.model.Member;
import com.javaoop.movie_booking_app.model.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    /**
     * Finds all bookings for a given member, ordered by the showtime's start time descending.
     * This brings the most recent or upcoming bookings to the top.
     *
     * @param member The member entity to find bookings for.
     * @return A list of bookings for the member.
     */
    List<Booking> findByMemberOrderByShowtimeStartTimeDesc(Member member);

    List<Booking> findByShowtimeOrderByShowtimeStartTimeDesc(Showtime showtime);
}