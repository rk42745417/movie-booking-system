package com.javaoop.movie_booking_app.repository;

import com.javaoop.movie_booking_app.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}


