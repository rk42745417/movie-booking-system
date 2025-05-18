package com.javaoop.movie_booking_app.service;

import com.javaoop.movie_booking_app.model.Showtime;
import database.Database;

import java.time.LocalDateTime;
import java.util.*;

public class ShowtimeService {
    public boolean addShowtime(String movieId, String screen, LocalDateTime startTime, int totalSeats) {
        for (Showtime s : Database.showtimes.values()) {
            if (s.screen.equals(screen)) {
                LocalDateTime sStart = s.startTime;
                LocalDateTime sEnd = s.startTime.plusHours(2);
                LocalDateTime newEnd = startTime.plusHours(2);
                boolean overlap = startTime.isBefore(sEnd) && sStart.isBefore(newEnd);
                if (overlap) return false;
            }
        }
        String id = UUID.randomUUID().toString();
        Showtime newShowtime = new Showtime(id, movieId, screen, startTime, totalSeats);
        Database.showtimes.put(id, newShowtime);
        return true;
    }

    public List<Showtime> getAllShowtimes() {
        return new ArrayList<>(Database.showtimes.values());
    }

    public List<Showtime> getShowtimesByScreen(String screen) {
        List<Showtime> list = new ArrayList<>();
        for (Showtime s : Database.showtimes.values()) {
            if (s.screen.equals(screen)) {
                list.add(s);
            }
        }
        return list;
    }

    public Showtime addShowtime(Showtime showtime) {
        return showtimeRepository.save(showtime);
    }
}

