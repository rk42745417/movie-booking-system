package com.javaoop.movie_booking_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * HomeController is responsible for handling the root URL ("/") of the application.
 * It directs users to the homepage.
 */
@Controller
public class HomeController {

    /**
     * Default no-argument constructor.
     */
    public HomeController() {
        // no-op
    }
    
    /**
     * Handles requests to the root URL ("/").
     *
     * @return the name of the Thymeleaf view to be rendered ("index")
     */
    @GetMapping()
    public String home() {
        return "index";
    }
}
