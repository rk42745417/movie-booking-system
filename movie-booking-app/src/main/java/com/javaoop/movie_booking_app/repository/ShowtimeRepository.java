package com.javaoop.movie_booking_app.repository;

import com.javaoop.movie_booking_app.model.Hall;
import com.javaoop.movie_booking_app.model.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {
    List<Showtime> findByMovieMovieId(Long movieId);

    List<Showtime> findByMovieMovieIdOrderByStartTimeDesc(Long movieId);

    /**
     * Finds any showtimes in the same Hall that overlap with the given time range.
     * This is used when creating a NEW showtime.
     */
    @Query("SELECT s FROM Showtime s WHERE :hall = s.hall AND :newStart < s.endTime AND :newEnd > s.startTime")
    List<Showtime> findOverlappingShowtimes(
            @Param("hall") Hall hall,
            @Param("newStart") LocalDateTime newStart,
            @Param("newEnd") LocalDateTime newEnd
    );


    /**
     * Finds any showtimes in the same Hall that overlap with the given time range, excluding a showtime with a specific ID.
     * This is crucial for UPDATE operations, to avoid a showtime colliding with itself.
     */
    @Query("SELECT s FROM Showtime s WHERE :hall = s.hall AND s.showtimeId != :excludeId AND :newStart < s.endTime AND :newEnd > s.startTime")
    List<Showtime> findOverlappingShowtimesForUpdate(
            @Param("hall") Hall hall,
            @Param("newStart") LocalDateTime newStart,
            @Param("newEnd") LocalDateTime newEnd,
            @Param("excludeId") Long excludeId
    );
}

