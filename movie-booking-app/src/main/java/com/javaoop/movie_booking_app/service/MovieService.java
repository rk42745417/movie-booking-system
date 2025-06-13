package com.javaoop.movie_booking_app.service;

import com.javaoop.movie_booking_app.model.Movie;
import com.javaoop.movie_booking_app.repository.MovieRepository;
//import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class to manage movie-related operations such as retrieval and status updates.
 */
@Service
public class MovieService {
    private final MovieRepository movieRepository;

    /**
     * Constructs a MovieService with the provided MovieRepository.
     *
     * @param movieRepository repository for accessing movie data
     */
    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    /**
     * Retrieves all movies in the database.
     *
     * @return a list of all Movie entities
     */
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    /**
     * Retrieves all active movies (e.g., currently screening or available).
     *
     * @return a list of Movie entities with active status true
     */
    public List<Movie> getAllActiveMovies() {
        return movieRepository.findByIsActive(true);
    }

    /**
     * Finds a movie by its unique ID.
     *
     * @param movieId the ID of the movie to find
     * @return an Optional containing the Movie if found, otherwise empty
     */
    public Optional<Movie> getMovie(Long movieId) {
        return movieRepository.findById(movieId);
    }

    /**
     * Updates the active status of a movie identified by its ID.
     *
     * @param movieId the ID of the movie to update
     * @param isActive the new active status to set
     * @throws IllegalStateException if the movie with the given ID does not exist
     */
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
