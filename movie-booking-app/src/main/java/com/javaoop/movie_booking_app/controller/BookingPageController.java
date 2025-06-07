package com.javaoop.movie_booking_app.controller;

import com.javaoop.movie_booking_app.model.Movie;
import com.javaoop.movie_booking_app.model.Showtime;
import com.javaoop.movie_booking_app.service.MovieService;
import com.javaoop.movie_booking_app.repository.ShowtimeRepository;
import com.javaoop.movie_booking_app.repository.HallRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class BookingPageController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Autowired
    private HallRepository hallRepository;

    @GetMapping("/booking-page")
    public String showBookingPage(
            @RequestParam("movieId") Long movieId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required = false) String hallType,
            Model model
    ) {
        Movie movie = movieService.getMovieById(movieId);

        List<Showtime> filteredShowtimes = showtimeRepository.findFilteredShowtimesByMovieAndFilters(
        		movieId, startTime, hallType);

        List<String> hallTypes = hallRepository.findAllDistinctHallTypes();

        model.addAttribute("movieId", movie.getMovieId());
        model.addAttribute("movieTitle", movie.getTitle());

        // ✅ Use the correct key expected by the Thymeleaf template
        model.addAttribute("sessionTimes", filteredShowtimes);

        model.addAttribute("hallTypes", hallTypes);
        model.addAttribute("selectedStartTime", startTime);
        model.addAttribute("selectedHallType", hallType);

        return "6訂票用戶＿訂票（場次）";
    }

}


