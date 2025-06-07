package com.javaoop.movie_booking_app.service;

import com.javaoop.movie_booking_app.model.Movie;
import com.javaoop.movie_booking_app.model.Showtime;
import com.javaoop.movie_booking_app.repository.MovieRepository;
import com.javaoop.movie_booking_app.repository.ShowtimeRepository;
import jakarta.transaction.Transactional;
import org.codehaus.groovy.transform.trait.Traits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShowtimeService {
    private final ShowtimeRepository showtimeRepository;
    private final MovieRepository movieRepository;

    @Autowired
    public ShowtimeService(ShowtimeRepository showtimeRepository, MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        this.showtimeRepository = showtimeRepository;
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
        return showtimeRepository.findByMovieMovieId(movieId);
    }
}

