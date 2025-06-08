package com.javaoop.movie_booking_app.controller;

import com.javaoop.movie_booking_app.dto.BookingFormDto;
import com.javaoop.movie_booking_app.exception.SeatUnavailableException;
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

@Controller
public class BookingController {
    private final BookingService bookingService;// To get booked seats
    private final ShowtimeService showtimeService;
    private final SeatService seatService;
    private final MemberService memberService;

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
     */
    @GetMapping("/booking/showtime/{showtimeId}")
    public String showSeatSelectionPage(@PathVariable("showtimeId") Long showtimeId, Model model, RedirectAttributes redirectAttributes) {
        // Find all seats for the hall of this showtime
        Optional<Showtime> showtimeResult = showtimeService.getShowtime(showtimeId);
        if (showtimeResult.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "找不到場次");
            return "redirect:";
        }
        Showtime showtime = showtimeResult.get();
        List<Seat> allSeatsInHall = seatService.getAllSeatInHall(showtime.getHall());

        // Find which seats are already booked
        Set<Long> bookedSeatIds = showtimeService.getBookedSeatIdsInShowtime(showtimeId);

        // Group the flat list of seats into a Map where key is the row letter.
        // We use a TreeMap to ensure the rows are sorted (A, B, C...).
        Map<Character, List<Seat>> seatsByRow = allSeatsInHall.stream()
                .collect(Collectors.groupingBy(Seat::getRow, TreeMap::new, Collectors.toList()));

        // Prepare the form-backing object
        BookingFormDto bookingForm = new BookingFormDto();

        model.addAttribute("seatsByRow", seatsByRow);
        model.addAttribute("bookedSeatIds", bookedSeatIds);
        model.addAttribute("showtime", showtime);
        model.addAttribute("bookingForm", bookingForm); // The object for the form

        return "booking_seat_selection";
    }

    /**
     * Processes the seat selection form submission.
     */
    @PostMapping("/booking/create/{showtimeId}")
    public String processBooking(
            @PathVariable("showtimeId") Long showtimeId,
            @Valid @ModelAttribute("bookingForm") BookingFormDto bookingForm,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        // Handle basic validation errors (e.g., no seats selected)
        if (bindingResult.hasErrors()) {
            // If validation fails, we must re-populate the model to re-render the page
            redirectAttributes.addFlashAttribute("errorMessage", "請至少選擇一個座位");
            return "redirect:/booking/showtime/" + showtimeId;
        }

        if (showtimeService.getShowtime(showtimeId).isEmpty()) {
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
            // Attempt to create the booking
            bookingService.createBooking(bookingForm, member.get().getMemberId(), showtimeId); // Assumes service takes the DTO
            redirectAttributes.addFlashAttribute("successMessage", "訂票成功");
            return "redirect:/booking";

        } catch (Exception e) {
            // Handle the specific business logic error (seats taken)
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/booking/showtime/" + showtimeId;
        }
    }

    /**
     * Displays a list of all bookings for the current user.
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
            // Catch all expected exceptions and show a user-friendly error message
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        // Redirect back to the booking list page after the action
        return "redirect:/booking";
    }
}