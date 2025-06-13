package com.javaoop.movie_booking_app.controller;

import com.javaoop.movie_booking_app.dto.BookingFormDto;
//import com.javaoop.movie_booking_app.exception.SeatUnavailableException;
import com.javaoop.movie_booking_app.model.Booking;
import com.javaoop.movie_booking_app.model.Member;
import com.javaoop.movie_booking_app.model.Seat;
import com.javaoop.movie_booking_app.model.Showtime;
import com.javaoop.movie_booking_app.service.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Controller responsible for managing the booking process including seat selection,
 * booking creation, booking history display, and booking cancellation.
 * <p>
 * This controller handles all booking-related requests for logged-in users.
 * It interacts with services for booking management, showtime details,
 * seat availability, and member information.
 * </p>
 */
@Controller
public class BookingController {

    private final BookingService bookingService;
    private final ShowtimeService showtimeService;
    private final SeatService seatService;
    private final MemberService memberService;

    /**
     * Constructs a new BookingController with required service dependencies.
     *
     * @param bookingService  service to handle booking operations
     * @param showtimeService service to handle showtime information
     * @param seatService     service to handle seat details
     * @param memberService   service to handle member information
     */
    public BookingController(BookingService bookingService,
                             ShowtimeService showtimeService,
                             SeatService seatService,
                             MemberService memberService) {
        this.bookingService = bookingService;
        this.seatService = seatService;
        this.memberService = memberService;
        this.showtimeService = showtimeService;
    }

    /**
     * Displays the seat selection page for a given showtime.
     * <p>
     * The method fetches the showtime by its ID, loads all seats in the related hall,
     * determines which seats are already booked, groups seats by row for display,
     * and prepares a booking form DTO for the view.
     * </p>
     *
     * @param showtimeId          the unique ID of the showtime for which seats are to be booked
     * @param model               Spring MVC Model to add attributes for the view
     * @param redirectAttributes  attributes used for passing flash messages on redirect
     * @return the Thymeleaf template name for seat selection page,
     * or redirect to the homepage if showtime not found
     */
    @GetMapping("/booking/showtime/{showtimeId}")
    public String showSeatSelectionPage(@PathVariable("showtimeId") Long showtimeId, Model model, RedirectAttributes redirectAttributes) {
        Optional<Showtime> showtimeResult = showtimeService.getShowtime(showtimeId);
        if (showtimeResult.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "找不到場次");
            return "redirect:";
        }

        Showtime showtime = showtimeResult.get();
        List<Seat> allSeatsInHall = seatService.getAllSeatInHall(showtime.getHall());
        Set<Long> bookedSeatIds = showtimeService.getBookedSeatIdsInShowtime(showtimeId);

        Map<Character, List<Seat>> seatsByRow = allSeatsInHall.stream()
                .collect(Collectors.groupingBy(Seat::getRow, TreeMap::new, Collectors.toList()));

        BookingFormDto bookingForm = new BookingFormDto();

        model.addAttribute("seatsByRow", seatsByRow);
        model.addAttribute("bookedSeatIds", bookedSeatIds);
        model.addAttribute("showtime", showtime);
        model.addAttribute("bookingForm", bookingForm);

        return "booking_seat_selection";
    }

    /**
     * Processes the seat selection form submitted by the user and attempts to create a booking.
     * <p>
     * This method validates the form data, checks the existence of the showtime and the current user,
     * and calls the booking service to create the booking. If errors occur, it redirects back to
     * the seat selection page with error messages.
     * </p>
     *
     * @param showtimeId          the unique ID of the showtime to book seats for
     * @param bookingForm         DTO containing the list of selected seats by the user
     * @param bindingResult       result of form validation checks
     * @param model               Spring MVC Model (not typically used for redirect)
     * @param redirectAttributes  attributes used to pass flash messages on redirect
     * @return redirect URL to the booking page on success or seat selection page on failure
     */
    @PostMapping("/booking/create/{showtimeId}")
    public String processBooking(
            @PathVariable("showtimeId") Long showtimeId,
            @Valid @ModelAttribute("bookingForm") BookingFormDto bookingForm,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "請至少選擇一個座位");
            return "redirect:/booking/showtime/" + showtimeId;
        }

        Optional<Showtime> showtime = showtimeService.getShowtime(showtimeId);
        if (showtime.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "找不到場次");
            return "redirect:/booking/showtime/" + showtimeId;
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Member> member = memberService.getByEmail(email);
        if (member.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "找不到使用者");
            return "redirect:/booking/showtime/" + showtimeId;
        }

        try {
            bookingService.createBooking(bookingForm, member.get().getMemberId(), showtimeId);
            redirectAttributes.addFlashAttribute("successMessage", "訂票成功");
            return "redirect:/booking";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/booking/showtime/" + showtimeId;
        }
    }

    /**
     * Displays the booking history page for the currently logged-in user.
     * <p>
     * The method retrieves the current authenticated user's email,
     * fetches their bookings, and adds them to the model for display.
     * Redirects to login if user information is missing.
     * </p>
     *
     * @param model               Spring MVC Model to pass bookings data to the view
     * @param redirectAttributes  attributes used to pass flash messages on redirect
     * @return the Thymeleaf template name for user's booking history or redirect to login page
     */
    @GetMapping("/booking")
    public String showMyBookings(Model model, RedirectAttributes redirectAttributes) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Member> member = memberService.getByEmail(email);
        if (member.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "找不到使用者");
            return "redirect:/login";
        }

        List<Booking> userBookings = bookingService.findBookingsForMember(member.get());
        model.addAttribute("bookings", userBookings);

        return "booking";
    }

    /**
     * Handles a booking cancellation request from the logged-in user.
     * <p>
     * Validates the user's identity, attempts to cancel the booking if it belongs to the user,
     * and provides success or error messages accordingly.
     * </p>
     *
     * @param bookingId           the ID of the booking to cancel
     * @param redirectAttributes  attributes used to pass flash messages on redirect
     * @return redirect URL back to the user's booking history page or login if user not found
     */
    @PostMapping("/booking/cancel")
    public String handleBookingCancellation(
            @RequestParam("bookingId") Long bookingId,
            RedirectAttributes redirectAttributes) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Member> member = memberService.getByEmail(email);
        if (member.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "找不到使用者");
            return "redirect:/login";
        }

        try {
            bookingService.cancelBooking(bookingId, member.get().getMemberId());
            redirectAttributes.addFlashAttribute("successMessage", "訂單" + bookingId + " 已成功取消");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/booking";
    }
}
