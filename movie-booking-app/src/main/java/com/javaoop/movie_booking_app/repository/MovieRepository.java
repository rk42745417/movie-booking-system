package com.javaoop.movie_booking_app.repository;

import com.javaoop.movie_booking_app.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByIsActive(boolean isActive);

    List<Movie> findByTitle(String title);
}



