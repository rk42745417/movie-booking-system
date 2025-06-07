package com.javaoop.movie_booking_app.controller;

import com.javaoop.movie_booking_app.dto.NewShowtimeDto;
import com.javaoop.movie_booking_app.service.MovieService;
import com.javaoop.movie_booking_app.service.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.javaoop.movie_booking_app.model.Movie;
import com.javaoop.movie_booking_app.model.Showtime;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final MovieService movieService;
    private final ShowtimeService showtimeService;

    @Autowired
    public AdminController(MovieService movieService, ShowtimeService showtimeService) {
        this.movieService = movieService;
        this.showtimeService = showtimeService;
    }

    @GetMapping()
    public String adminHome(Model model) {
        model.addAttribute("movies", movieService.getAllMovies());
        return "admin_home";
    }

    @GetMapping("/movie/{id}")
    public String adminMovie(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Movie> movie = movieService.getMovie(id);
        if (movie.isPresent()) {
            model.addAttribute("movie", movie.get());
            return "admin_movie";
        }
        redirectAttributes.addFlashAttribute("errorMessage", "電影不存在");
        return "redirect:/admin";
    }

    @PostMapping("/movie/{id}")
    public String adminControlMovie(@PathVariable("id") Long id, @RequestParam(name = "active", required = false) String activeParam, RedirectAttributes redirectAttributes) {
        boolean isActive = activeParam != null;
        try {
            movieService.updateActiveState(id, isActive);
            redirectAttributes.addFlashAttribute("successMessage", "修改成功");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/movie/" + id;
    }

    @GetMapping("/movie/{id}/showtime")
    public String adminGetShowtime(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        List<Showtime> showtimes;
        try {
            showtimes = showtimeService.getMovieShowtimes(id);
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin";
        }
        Movie movie = movieService.getMovie(id).get();

        model.addAttribute("showtimes", showtimes);
        model.addAttribute("movie", movie);
        model.addAttribute("new_showtime", new NewShowtimeDto());
        return "admin_movie_showtime";
    }


}
