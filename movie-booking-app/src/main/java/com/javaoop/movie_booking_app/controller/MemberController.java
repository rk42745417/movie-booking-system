package com.javaoop.movie_booking_app.controller;

import com.javaoop.movie_booking_app.dto.RegistrationDto;
import com.javaoop.movie_booking_app.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class MemberController {
    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/login")
    public String login() {
        return "user_login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        // Pass an empty DTO to the form
        model.addAttribute("user", new RegistrationDto());
        return "user_register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") RegistrationDto registrationDto,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "user_register";
        }

        try {
            memberService.register(registrationDto);
        } catch (IllegalStateException e) {
            bindingResult.rejectValue("email", "user.exists", e.getMessage());
            return "user_register";
        }

        redirectAttributes.addFlashAttribute("successMessage", "註冊成功！請登入");
        return "redirect:/user/login";
    }
}
