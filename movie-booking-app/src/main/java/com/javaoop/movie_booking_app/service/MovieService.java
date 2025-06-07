package com.javaoop.movie_booking_app.service;

import com.javaoop.movie_booking_app.model.Movie;
import com.javaoop.movie_booking_app.model.RatingCategory;
import com.javaoop.movie_booking_app.repository.MovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;



@Service
public class MovieService{
    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie addMovie(String title, String synopsis, Integer durationMinutes, RatingCategory ratingCategory) {
        Movie movie = new Movie(title, synopsis, durationMinutes, ratingCategory);
        return movieRepository.save(movie);
    }

    @Transactional
    public void removeMovie(Long movieId) {
        if (movieRepository.findById(movieId).isEmpty()) {
            throw new IllegalStateException("Movie not exists");
        }
        movieRepository.deleteById(movieId);
    }

    public List<Movie> getCurrentMovies() {
        return movieRepository.findByIsActive(true);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> getMovie(Long movieId) {
        return movieRepository.findById(movieId);
    }
    
    public Movie getMovieById(Long movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new NoSuchElementException("找不到 movieId = " + movieId + " 的電影"));
    }
}
