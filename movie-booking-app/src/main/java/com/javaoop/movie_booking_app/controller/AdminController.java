package com.javaoop.movie_booking_app.controller;

import com.javaoop.movie_booking_app.exception.ShowtimeCollisionException;
import com.javaoop.movie_booking_app.dto.ShowtimeDto;
import com.javaoop.movie_booking_app.model.BookingStatus;
import com.javaoop.movie_booking_app.service.BookingService;
import com.javaoop.movie_booking_app.service.HallService;
import com.javaoop.movie_booking_app.service.MovieService;
import com.javaoop.movie_booking_app.service.ShowtimeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    private final HallService hallService;
    private final BookingService bookingService;

    @Autowired
    public AdminController(MovieService movieService, ShowtimeService showtimeService, HallService hallService, BookingService bookingService) {
        this.movieService = movieService;
        this.showtimeService = showtimeService;
        this.hallService = hallService;
        this.bookingService = bookingService;
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
        model.addAttribute("new_showtime", new ShowtimeDto());
        model.addAttribute("hall_option", hallService.getAllHalls());
        return "admin_movie_showtime";
    }


    @PostMapping("/movie/{id}/showtime")
    public String adminAddShowTime(@PathVariable("id") Long id,
                                   @Valid @ModelAttribute("new_showtime") ShowtimeDto showtimeDto,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "場次資訊不合法");
        } else {
            try {
                showtimeService.addNewShowtime(id, showtimeDto);
                redirectAttributes.addFlashAttribute("successMessage", "成功新增上映場次");
            } catch (IllegalStateException | ShowtimeCollisionException e) {
                redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            }
        }
        return "redirect:/admin/movie/" + id + "/showtime";
    }

    @GetMapping("/showtime/{showtimeId}")
    public String adminEditShowtime(@PathVariable("showtimeId") Long showtimeId, Model model, RedirectAttributes redirectAttributes) {
        Optional<Showtime> showtimeResult = showtimeService.getShowtime(showtimeId);
        if (showtimeResult.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "找不到上映場次");
            return "redirect:/admin";
        }
        Showtime showtime = showtimeResult.get();

        model.addAttribute("showtime", showtime);

        ShowtimeDto showtimeForm = new ShowtimeDto();
        showtimeForm.setHallId(showtime.getHall().getHallId());
        showtimeForm.setStartTime(showtime.getStartTime());
        model.addAttribute("showtimeForm", showtimeForm);

        model.addAttribute("hall_option", hallService.getAllHalls());
        return "admin_showtime";
    }

    @PostMapping("/showtime/{id}")
    public String adminUpdateShowtime(@PathVariable("id") Long id,
                                      @Valid @ModelAttribute("showtimeForm") ShowtimeDto showtimeDto,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "場次資訊不合法");
            return "redirect:/admin/showtime/" + id;
        } else {
            try {
                showtimeService.updateShowtime(id, showtimeDto);
                redirectAttributes.addFlashAttribute("successMessage", "成功更新上映場次");
                Movie movie = showtimeService.getShowtime(id).get().getMovie();
                return "redirect:/admin/movie/" + movie.getMovieId() + "/showtime";
            } catch (IllegalStateException | ShowtimeCollisionException e) {
                redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
                return "redirect:/admin";
            }
        }
    }

    @PostMapping("/booking")
    public String handleStatusUpdate(
            @RequestParam("bookingId") Long bookingId,
            @RequestParam("status") BookingStatus status,
            @RequestParam("showtimeId") Optional<Long> showtimeId,
            RedirectAttributes redirectAttributes) {

        try {
            bookingService.updateBookingStatus(bookingId, status);
            redirectAttributes.addFlashAttribute("successMessage", "訂單編號 " + bookingId + " 成功更新成 " + status);
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        // Redirect back to the same page of the bookings list the admin was on
        return showtimeId.map(aLong -> "redirect:/admin/booking/" + aLong).orElse("redirect:/admin/booking");
    }

    @GetMapping("/booking")
    public String adminListBookings(Model model) {
        model.addAttribute("bookings", bookingService.findAllBookings());
        model.addAttribute("allStatuses", BookingStatus.values());
        return "admin_booking";
    }

    @GetMapping("/booking/{showtimeId}")
    public String adminListBookingsByShowtime(@PathVariable("showtimeId") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Showtime> showtimeResult = showtimeService.getShowtime(id);
        if (showtimeResult.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "找不到場次");
            return "redirect:/admin";
        }
        model.addAttribute("bookings", bookingService.findBookingsForShowtime(showtimeResult.get()));
        model.addAttribute("allStatuses", BookingStatus.values());
        model.addAttribute("showtimeId", id);
        return "admin_booking";
    }
}
