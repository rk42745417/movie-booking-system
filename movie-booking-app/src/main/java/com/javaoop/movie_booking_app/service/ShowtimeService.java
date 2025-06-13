package com.javaoop.movie_booking_app.service;

import com.javaoop.movie_booking_app.exception.ShowtimeCollisionException;
import com.javaoop.movie_booking_app.dto.ShowtimeDto;
import com.javaoop.movie_booking_app.model.Hall;
import com.javaoop.movie_booking_app.model.Movie;
import com.javaoop.movie_booking_app.model.Showtime;
import com.javaoop.movie_booking_app.repository.HallRepository;
import com.javaoop.movie_booking_app.repository.MovieRepository;
import com.javaoop.movie_booking_app.repository.SeatRepository;
import com.javaoop.movie_booking_app.repository.ShowtimeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service class for managing showtime operations such as creation, update,
 * retrieval, and conflict checking for movie showtimes in halls.
 */
@Service
public class ShowtimeService {
    private final ShowtimeRepository showtimeRepository;
    private final MovieRepository movieRepository;
    private final HallRepository hallRepository;
    private final SeatRepository seatRepository;

    /**
     * Constructs a ShowtimeService with the required repositories.
     *
     * @param showtimeRepository repository for showtime data access
     * @param movieRepository    repository for movie data access
     * @param hallRepository     repository for hall data access
     * @param seatRepository     repository for seat data access
     */
    @Autowired
    public ShowtimeService(ShowtimeRepository showtimeRepository, MovieRepository movieRepository, HallRepository hallRepository, SeatRepository seatRepository) {
        this.movieRepository = movieRepository;
        this.showtimeRepository = showtimeRepository;
        this.hallRepository = hallRepository;
        this.seatRepository = seatRepository;
    }

    /**
     * Retrieves a showtime by its ID.
     *
     * @param showtimeId the ID of the showtime
     * @return an Optional containing the showtime if found, otherwise empty
     */
    public Optional<Showtime> getShowtime(Long showtimeId) {
        return showtimeRepository.findById(showtimeId);
    }

    /**
     * Retrieves all showtimes for a given movie, ordered by start time descending.
     * Throws an IllegalStateException if the movie does not exist or is inactive.
     *
     * @param movieId the ID of the movie
     * @return a list of showtimes for the movie
     * @throws IllegalStateException if movie is not found or inactive
     */
    @Transactional
    public List<Showtime> getMovieShowtimes(Long movieId) throws IllegalStateException {
        Optional<Movie> movieResult = movieRepository.findById(movieId);
        if (movieResult.isEmpty()) {
            throw new IllegalStateException("電影不存在");
        }
        Movie movie = movieResult.get();
        if (!movie.isActive()) {
            throw new IllegalStateException("電影未上映");
        }
        return showtimeRepository.findByMovieMovieIdOrderByStartTimeDesc(movieId);
    }

    /**
     * Adds a new showtime for a movie in a specific hall.
     * Validates movie existence and active state, hall existence, and time conflicts.
     *
     * @param movieId     the ID of the movie
     * @param showtimeDto the data transfer object containing showtime details
     * @throws IllegalStateException     if movie or hall does not exist or movie inactive
     * @throws ShowtimeCollisionException if there is a scheduling conflict in the hall
     */
    @Transactional
    public void addNewShowtime(Long movieId, ShowtimeDto showtimeDto) throws IllegalStateException, ShowtimeCollisionException {
        Optional<Movie> movieResult = movieRepository.findById(movieId);
        if (movieResult.isEmpty()) {
            throw new IllegalStateException("電影不存在");
        }
        Movie movie = movieResult.get();
        if (!movie.isActive()) {
            throw new IllegalStateException("電影未處於上映狀態");
        }

        Optional<Hall> hallResult = hallRepository.findById(showtimeDto.getHallId());
        if (hallResult.isEmpty()) {
            throw new IllegalStateException("放映廳不存在");
        }
        Hall hall = hallResult.get();

        LocalDateTime endTime = showtimeDto.getStartTime().plusMinutes(movie.getDurationMinutes());

        List<Showtime> collisions = showtimeRepository.findOverlappingShowtimes(
                hall,
                showtimeDto.getStartTime(),
                endTime
        );

        if (!collisions.isEmpty()) {
            throw new ShowtimeCollisionException("該時間已有電影");
        }

        showtimeRepository.save(new Showtime(
                movie,
                hall,
                showtimeDto.getStartTime(),
                endTime
        ));
    }

    /**
     * Updates an existing showtime's start time and hall, ensuring no schedule conflicts.
     *
     * @param showtimeId  the ID of the showtime to update
     * @param showtimeDto the data transfer object containing new showtime details
     * @throws IllegalStateException     if showtime or hall does not exist
     * @throws ShowtimeCollisionException if scheduling conflicts exist with other showtimes
     */
    @Transactional
    public void updateShowtime(Long showtimeId, ShowtimeDto showtimeDto) throws IllegalStateException, ShowtimeCollisionException {
        Optional<Showtime> showtimeResult = showtimeRepository.findById(showtimeId);
        if (showtimeResult.isEmpty()) {
            throw new IllegalStateException("場次不存在");
        }
        Showtime showtime = showtimeResult.get();
        Movie movie = showtime.getMovie();

        Optional<Hall> hallResult = hallRepository.findById(showtimeDto.getHallId());
        if (hallResult.isEmpty()) {
            throw new IllegalStateException("放映廳不存在");
        }
        Hall hall = hallResult.get();

        LocalDateTime endTime = showtimeDto.getStartTime().plusMinutes(movie.getDurationMinutes());

        List<Showtime> collisions = showtimeRepository.findOverlappingShowtimesForUpdate(
                hall,
                showtimeDto.getStartTime(),
                endTime,
                showtime.getShowtimeId()
        );

        if (!collisions.isEmpty()) {
            throw new ShowtimeCollisionException("該時間已有電影");
        }

        showtime.setStartTime(showtimeDto.getStartTime());
        showtime.setHall(hall);
        showtime.setEndTime(endTime);

        showtimeRepository.save(showtime);
    }

    /**
     * Retrieves the set of seat IDs that are already booked for a given showtime.
     *
     * @param showtimeId the ID of the showtime
     * @return a set of seat IDs that are booked
     */
    public Set<Long> getBookedSeatIdsInShowtime(Long showtimeId) {
        return seatRepository.findBookedSeatIdsByShowtimeId(showtimeId);
    }
}
