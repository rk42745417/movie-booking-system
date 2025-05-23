package com.javaoop.movie_booking_app.controller;

import com.javaoop.movie_booking_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
//    private final UserService userService;
//
//    @Autowired
//    public AuthController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @GetMapping("/login")
//    public String showLoginForm() {
//        return "login";
//    }
//
//    @PostMapping("/login")
//    public String loginUser(@RequestParam String email, @RequestParam String password) {
//        return userService.findByEmail(email) != null ? "redirect:/user/home" : "redirect:/login?error=true";
//    }
//
//    @GetMapping("/register")
//    public String showRegistrationForm() {
//        return "register";
//    }
//
//    @PostMapping("/register")
//    public String registerUser(@ModelAttribute User user) {
//        userService.registerUser(user);
//        return "redirect:/login";
//    }
}


