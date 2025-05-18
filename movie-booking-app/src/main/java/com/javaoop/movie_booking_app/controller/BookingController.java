package com.javaoop.movie_booking_app.controller;

import com.javaoop.movie_booking_app.model.Booking;
import com.javaoop.movie_booking_app.model.User;
import com.javaoop.movie_booking_app.service.BookingService;
import com.javaoop.movie_booking_app.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookingController {
    private final BookingService bookingService;
    private final MovieService movieService;

    @Autowired
    public BookingController(BookingService bookingService, MovieService movieService) {
        this.bookingService = bookingService;
        this.movieService = movieService;
    }

    @GetMapping("/book-movie/{movieId}")
    public String showBookingPage(@PathVariable int movieId, Model model) {
        model.addAttribute("movie", movieService.getMovieById(movieId));
        return "booking";
    }

    @PostMapping("/book-movie")
    public String bookMovie(@ModelAttribute Booking booking) {
        bookingService.bookTicket(booking);
        return "redirect:/booking-history";
    }

    @GetMapping("/booking-history")
    public String showBookingHistory(Model model, @SessionAttribute User user) {
        model.addAttribute("bookings", bookingService.getBookingsByUser(user));
        return "booking_history";
    }
}

