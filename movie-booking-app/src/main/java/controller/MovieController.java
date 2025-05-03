package controller;

import service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MovieController {
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movies")
    public String getAllMovies(Model model) {
        model.addAttribute("movies", movieService.getAllMovies());
        return "movie_list";
    }

    @GetMapping("/movie-details/{id}")
    public String getMovieDetails(@PathVariable int id, Model model) {
        model.addAttribute("movie", movieService.getMovieById(id));
        return "movie_details";
    }
}

