/**
 * 
 */
package com.javaoop.movie_booking_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 
 */
@Controller
public class MoviePageController {
    @GetMapping("/movies-page")
    public String showMoviePage() {
        return "5訂票用戶＿電影資訊查詢"; // Thymeleaf template, no ".html"
    }
}
