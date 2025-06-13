package com.javaoop.movie_booking_app.repository;

import com.javaoop.movie_booking_app.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link Movie} entities.
 * 
 * Extends {@link JpaRepository} to provide CRUD operations and pagination
 * capabilities for Movie objects.
 * 
 * Provides methods to find movies by their active status and by exact title.
 */
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    /**
     * Finds all movies with the specified active status.
     * 
     * @param isActive whether the movie is active (true) or inactive (false)
     * @return list of movies matching the active status
     */
    List<Movie> findByIsActive(boolean isActive);

    /**
     * Finds all movies with the exact specified title.
     * 
     * @param title the exact title of the movie to search for
     * @return list of movies with the matching title
     */
    List<Movie> findByTitle(String title);
}
