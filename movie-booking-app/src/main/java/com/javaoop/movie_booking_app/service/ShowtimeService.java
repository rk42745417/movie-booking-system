package com.javaoop.movie_booking_app.service;

import com.javaoop.movie_booking_app.model.Showtime;
import com.javaoop.movie_booking_app.repository.ShowtimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ShowtimeService {

    private final ShowtimeRepository showtimeRepository;

    @Autowired
    public ShowtimeService(ShowtimeRepository showtimeRepository) {
        this.showtimeRepository = showtimeRepository;
    }

    public Showtime findByStartTime(LocalDateTime sessionTime) {
        Optional<Showtime> result = showtimeRepository.findByStartTime(sessionTime);
        return result.orElse(null);
    }
}
