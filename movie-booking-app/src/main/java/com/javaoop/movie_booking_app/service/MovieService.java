package com.javaoop.movie_booking_app.service;

import com.javaoop.movie_booking_app.model.Movie;
import com.javaoop.movie_booking_app.model.RatingCategory;
import com.javaoop.movie_booking_app.repository.MovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public List<Movie> getAllActiveMovies() {
        return movieRepository.findByIsActive(true);
    }

    public Optional<Movie> getMovie(Long movieId) {
        return movieRepository.findById(movieId);
    }

    public void updateActiveState(Long movieId, boolean isActive) throws IllegalStateException {
        Optional<Movie> movie = movieRepository.findById(movieId);
        if (movie.isPresent()) {
            movie.get().setActive(isActive);
            movieRepository.save(movie.get());
            return;
        }
        throw new IllegalStateException("電影不存在");
    }
}
