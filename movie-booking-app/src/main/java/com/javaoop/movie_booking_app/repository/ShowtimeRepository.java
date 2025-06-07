package com.javaoop.movie_booking_app.repository;

import com.javaoop.movie_booking_app.model.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Showtime entity.
 * Provides methods to query showtimes by start time and date range.
 */
@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {

    /**
     * Find a showtime by its exact start time.
     *
     * @param startTime the exact session start time
     * @return optional showtime
     */
    Optional<Showtime> findByStartTime(LocalDateTime startTime);

    /**
     * Find all showtimes between two timestamps.
     *
     * @param start start of the time window
     * @param end   end of the time window
     * @return list of showtimes
     */
    List<Showtime> findAllByStartTimeBetween(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT s FROM Showtime s " +
    	       "WHERE s.movie.movieId = :movieId " +
    	       "AND (:startTime IS NULL OR s.startTime >= :startTime) " +
    	       "AND (:hallType IS NULL OR LOWER(s.hall.hallType) = LOWER(:hallType)) " +
    	       "ORDER BY s.startTime ASC")
    	List<Showtime> findFilteredShowtimesByMovieAndFilters(
    	    @Param("movieId") Long movieId,
    	    @Param("startTime") LocalDateTime startTime,
    	    @Param("hallType") String hallType);
}

