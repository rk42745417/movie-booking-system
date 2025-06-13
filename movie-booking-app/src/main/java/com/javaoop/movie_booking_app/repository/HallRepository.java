package com.javaoop.movie_booking_app.repository;

import com.javaoop.movie_booking_app.model.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link Hall} entities.
 * 
 * Extends {@link JpaRepository} to provide CRUD operations and pagination
 * capabilities for Hall objects.
 */
@Repository
public interface HallRepository extends JpaRepository<Hall, Long> {
}
