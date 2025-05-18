package com.javaoop.movie_booking_app.service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.javaoop.movie_booking_app.model.Movie;
import com.javaoop.movie_booking_app.model.Showtime;
import com.javaoop.movie_booking_app.model.User;
import database.Database;

public class BookingService {
    public boolean bookTicket(String userId, String showtimeId, int count, List<String> seats) {
        Showtime showtime = Database.showtimes.get(showtimeId);
        Movie movie = Database.movies.get(showtime.movieId);
        User user = Database.users.get(userId);

        if (showtime == null || movie == null || user == null) return false;

        // 年齡驗證
        if (movie.rating.equals("R") && user.age < 18) return false;
        if (movie.rating.equals("PG-13") && user.age < 13) return false;

        // 座位檢查
        if (showtime.availableSeats < count) return false;

        // 建立訂單
        showtime.availableSeats -= count;
        String ticketId = UUID.randomUUID().toString();
        Ticket ticket = new Ticket(ticketId, userId, showtimeId, seats);
        Database.tickets.put(ticketId, ticket);
        return true;
    }

    public List<Ticket> getUserTickets(String userId) {
        return Database.tickets.values().stream()
            .filter(t -> t.userId.equals(userId))
            .collect(Collectors.toList());
    }

    public boolean cancelTicket(String ticketId) {
        Ticket ticket = Database.tickets.get(ticketId);
        if (ticket == null || ticket.isCanceled) return false;

        Showtime showtime = Database.showtimes.get(ticket.showtimeId);
        if (showtime == null) return false;

        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(showtime.startTime.minusMinutes(30))) {
            return false; // 無法退票：距離開場不到30分鐘
        }

        ticket.isCanceled = true;
        showtime.availableSeats += ticket.seatNumbers.size();
        return true;
    }
}
