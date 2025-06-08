package com.javaoop.movie_booking_app.repository;

import com.javaoop.movie_booking_app.model.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HallRepository extends JpaRepository<Hall, Long> {
}



