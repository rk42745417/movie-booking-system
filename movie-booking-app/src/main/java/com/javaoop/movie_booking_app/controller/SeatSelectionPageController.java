/**
 * 
 */
package com.javaoop.movie_booking_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/seat-selection")
public class SeatSelectionPageController {

    @GetMapping("/small")
    public String showSmallRoomSeatPage(Model model) {
        // Model attributes set by SeatSelectionController are available here
        // Render the Thymeleaf template for 小廳座位
        return "8訂票用戶＿訂票（小廳座位）";
    }

    @GetMapping("/big")
    public String showBigRoomSeatPage(Model model) {
        // Model attributes set by SeatSelectionController are available here
        // Render the Thymeleaf template for 大廳座位
        return "7訂票用戶＿訂票（大廳座位）";
    }
}

