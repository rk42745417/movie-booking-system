package com.javaoop.movie_booking_app.repository;

import com.javaoop.movie_booking_app.model.Hall;
import com.javaoop.movie_booking_app.model.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
//import java.util.Set;

/**
 * Repository interface for managing {@link Showtime} entities.
 * Extends JpaRepository to provide standard CRUD operations and
 * defines custom queries for finding showtimes related to movies,
 * halls, and detecting scheduling overlaps.
 */
@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {

    /**
     * Retrieves a list of showtimes for a specific movie.
     *
     * @param movieId the ID of the movie
     * @return list of showtimes associated with the movie
     */
    List<Showtime> findByMovieMovieId(Long movieId);

    /**
     * Retrieves a list of showtimes for a specific movie,
     * ordered by start time in descending order.
     *
     * @param movieId the ID of the movie
     * @return list of showtimes ordered by start time (newest first)
     */
    List<Showtime> findByMovieMovieIdOrderByStartTimeDesc(Long movieId);

    /**
     * Finds any showtimes in the given hall that overlap with a specified
     * time interval.
     * <p>
     * This query is typically used when creating a new showtime to ensure
     * that the new showtime does not conflict with existing showtimes in
     * the same hall.
     * </p>
     *
     * @param hall     the Hall to check for overlapping showtimes
     * @param newStart the proposed new showtime start time
     * @param newEnd   the proposed new showtime end time
     * @return list of showtimes that overlap with the given time range in the hall
     */
    @Query("SELECT s FROM Showtime s WHERE :hall = s.hall AND :newStart < s.endTime AND :newEnd > s.startTime")
    List<Showtime> findOverlappingShowtimes(
            @Param("hall") Hall hall,
            @Param("newStart") LocalDateTime newStart,
            @Param("newEnd") LocalDateTime newEnd
    );

    /**
     * Finds any showtimes in the given hall that overlap with a specified
     * time interval, excluding a particular showtime by its ID.
     * <p>
     * This query is useful during showtime updates, to ensure that the
     * updated showtime's new schedule does not overlap with other showtimes,
     * excluding itself.
     * </p>
     *
     * @param hall      the Hall to check for overlapping showtimes
     * @param newStart  the proposed updated showtime start time
     * @param newEnd    the proposed updated showtime end time
     * @param excludeId the ID of the showtime to exclude from the check (usually the one being updated)
     * @return list of overlapping showtimes excluding the specified showtime
     */
    @Query("SELECT s FROM Showtime s WHERE :hall = s.hall AND s.showtimeId != :excludeId AND :newStart < s.endTime AND :newEnd > s.startTime")
    List<Showtime> findOverlappingShowtimesForUpdate(
            @Param("hall") Hall hall,
            @Param("newStart") LocalDateTime newStart,
            @Param("newEnd") LocalDateTime newEnd,
            @Param("excludeId") Long excludeId
    );
}
