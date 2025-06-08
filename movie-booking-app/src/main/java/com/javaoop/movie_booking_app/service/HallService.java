package com.javaoop.movie_booking_app.service;

import com.javaoop.movie_booking_app.model.Hall;
import com.javaoop.movie_booking_app.model.HallType;
import com.javaoop.movie_booking_app.model.Seat;
import com.javaoop.movie_booking_app.repository.HallRepository;
import com.javaoop.movie_booking_app.repository.SeatRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HallService {
    private final HallRepository hallRepository;
    private final SeatRepository seatRepository;

    private record SeatCount(Character row, Integer countPerRow) {
    }

    private SeatCount getSeatCount(HallType hallType) {
        return switch (hallType) {
            case BIG -> new SeatCount('L', 30);
            case SMALL -> new SeatCount('I', 16);
        };
    }

    @Autowired
    public HallService(HallRepository hallRepository, SeatRepository seatRepository) {
        this.hallRepository = hallRepository;
        this.seatRepository = seatRepository;
    }

    public List<Hall> getAllHalls() {
        return hallRepository.findAll();
    }

    public Optional<Hall> getHallById(Long id) {
        return hallRepository.findById(id);
    }

    @Transactional
    public void createHall(Hall hall) {
        hall = hallRepository.save(hall);

        SeatCount seatCount = getSeatCount(hall.getHallType());
        for (char row = 'A'; row <= seatCount.row; row++) {
            for (int i = 1; i <= seatCount.countPerRow; i++) {
                seatRepository.save(new Seat(hall, row, i));
            }
        }
    }
}

