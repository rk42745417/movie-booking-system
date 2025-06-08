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

@Service
public class ShowtimeService {
    private final ShowtimeRepository showtimeRepository;
    private final MovieRepository movieRepository;
    private final HallRepository hallRepository;
    private final SeatRepository seatRepository;

    @Autowired
    public ShowtimeService(ShowtimeRepository showtimeRepository, MovieRepository movieRepository, HallRepository hallRepository, SeatRepository seatRepository) {
        this.movieRepository = movieRepository;
        this.showtimeRepository = showtimeRepository;
        this.hallRepository = hallRepository;
        this.seatRepository = seatRepository;
    }

    public Optional<Showtime> getShowtime(Long showtimeId) {
        return showtimeRepository.findById(showtimeId);
    }

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

    public Set<Long> getBookedSeatIdsInShowtime(Long showtimeId) {
        return seatRepository.findBookedSeatIdsByShowtimeId(showtimeId);
    }
}