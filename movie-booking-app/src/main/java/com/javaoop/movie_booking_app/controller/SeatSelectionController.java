package com.javaoop.movie_booking_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/booking/seats")
public class SeatSelectionController {

    @GetMapping("/small")
    public String selectSmallRoomSeats(
            @RequestParam("showtimeId") Long showtimeId,
            @RequestParam("tickets") int tickets,
            Model model) {

        // Add all info needed for the seat selection page
        model.addAttribute("showtimeId", showtimeId);
        model.addAttribute("tickets", tickets);
        model.addAttribute("hallType", "small_room");

        // You can add more seat layout or availability info here

        // Forward internally to the page controller to serve the actual HTML
        return "forward:/seat-selection/small";
    }

    @GetMapping("/big")
    public String selectBigRoomSeats(
            @RequestParam("showtimeId") Long showtimeId,
            @RequestParam("tickets") int tickets,
            Model model) {

        model.addAttribute("showtimeId", showtimeId);
        model.addAttribute("tickets", tickets);
        model.addAttribute("hallType", "big_room");

        // Add additional data if needed

        return "forward:/seat-selection/big";
    }
}
