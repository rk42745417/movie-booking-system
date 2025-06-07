package com.javaoop.movie_booking_app.controller;

import com.javaoop.movie_booking_app.service.MovieService;
import com.javaoop.movie_booking_app.service.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.javaoop.movie_booking_app.model.Movie;
import com.javaoop.movie_booking_app.model.Showtime;

@Controller
@RequestMapping("/admin")
public class AdminController {
//    private final MovieService movieService;
//    private final ShowtimeService showtimeService;
//
//    @Autowired
//    public AdminController(MovieService movieService, ShowtimeService showtimeService) {
//        this.movieService = movieService;
//        this.showtimeService = showtimeService;
//    }
//
//    @GetMapping("/admin/home")
//    public String adminHome(Model model) {
//        model.addAttribute("movies", movieService.getAllMovies());
//        return "admin_home";
//    }
//
//    @GetMapping("/admin/add-movie")
//    public String showAddMovieForm() {
//        return "add_movie";
//    }
//
//    @PostMapping("/admin/add-movie")
//    public String addMovie(@ModelAttribute Movie movie) {
//        movieService.addMovie(movie);
//        return "redirect:/admin/home";
//    }
//
//    @GetMapping("/admin/manage-showtimes")
//    public String showManageShowtimes(Model model) {
//        model.addAttribute("showtimes", showtimeService.getAllShowtimes());
//        return "manage_showtimes";
//    }
//
//    @PostMapping("/admin/add-showtime")
//    public String addShowtime(@ModelAttribute Showtime showtime) {
//        showtimeService.addShowtime(showtime);
//        return "redirect:/admin/manage-showtimes";
//    }
}
