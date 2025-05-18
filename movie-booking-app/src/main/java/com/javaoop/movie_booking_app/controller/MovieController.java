package com.javaoop.movie_booking_app.controller;

import com.javaoop.movie_booking_app.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public String getAllMovies(Model model) {
        System.out.println(movieService.getAllMovies().toString());
        model.addAttribute("movies", movieService.getAllMovies());
        return "movie_list";
    }

//    @GetMapping("/{id}")
//    public String getMovieDetails(@PathVariable Long id, Model model) {
//        model.addAttribute("movie", movieService.getMovie(id));
//        return "movie_details";
//    }
}

