package com.javaoop.movie_booking_app.controller;

import com.javaoop.movie_booking_app.model.Movie;
import com.javaoop.movie_booking_app.service.MovieService;
import com.javaoop.movie_booking_app.service.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

/**
 * Controller responsible for displaying movie information and associated showtimes.
 */
@Controller
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;
    private final ShowtimeService showtimeService;

    /**
     * Constructor-based dependency injection of movie and showtime services.
     *
     * @param movieService    the service for handling movie-related logic
     * @param showtimeService the service for handling showtime-related logic
     */
    @Autowired
    public MovieController(MovieService movieService, ShowtimeService showtimeService) {
        this.movieService = movieService;
        this.showtimeService = showtimeService;
    }

    /**
     * Handles request to display all active movies.
     *
     * @param model the model to pass movie data to the view
     * @return the name of the view that displays the movie list
     */
    @GetMapping
    public String getAllMovies(Model model) {
        model.addAttribute("movies", movieService.getAllActiveMovies());
        return "movie_list";
    }

    /**
     * Displays showtimes for a specific movie by movie ID.
     *
     * @param movieId            the ID of the movie to view showtimes for
     * @param model              the model to pass movie and showtime data
     * @param redirectAttributes attributes for passing error messages across redirects
     * @return the name of the view showing showtimes or a redirect if movie not found
     */
    @GetMapping("/{movieId}/showtime")
    public String getShowTimesOfMovie(@PathVariable("movieId") Long movieId,
                                      Model model,
                                      RedirectAttributes redirectAttributes) {
        try {
            Optional<Movie> movieResult = movieService.getMovie(movieId);
            if (movieResult.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "電影不存在");
                return "redirect:/movie";
            }

            model.addAttribute("movie", movieResult.get());
            model.addAttribute("showtimes", showtimeService.getMovieShowtimes(movieId));
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/movie";
        }

        return "movie_showtime";
    }
}
