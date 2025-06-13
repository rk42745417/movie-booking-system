package com.javaoop.movie_booking_app.controller;

import com.javaoop.movie_booking_app.dto.ChangePasswordDto;
import com.javaoop.movie_booking_app.dto.RegistrationDto;
import com.javaoop.movie_booking_app.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller that handles member-related actions such as login, registration,
 * and password changes.
 */
@Controller
@RequestMapping("/user")
public class MemberController {

    private final MemberService memberService;

    /**
     * Constructor-based dependency injection of MemberService.
     *
     * @param memberService the service handling member business logic
     */
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * Displays the login page.
     *
     * @return the name of the login view
     */
    @GetMapping("/login")
    public String login() {
        return "user_login";
    }

    /**
     * Displays the registration form.
     *
     * @param model the model to carry form data
     * @return the name of the registration view
     */
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new RegistrationDto());
        return "user_register";
    }

    /**
     * Handles user registration form submission.
     *
     * @param registrationDto      DTO carrying user registration data
     * @param bindingResult        result of validation
     * @param redirectAttributes   for passing flash messages
     * @return the view to be rendered or redirect URL
     */
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

    /**
     * Displays the change password form.
     *
     * @param model the model to carry form data
     * @return the name of the change password view
     */
    @GetMapping("/change_password")
    public String changePassword(Model model) {
        model.addAttribute("user", new ChangePasswordDto());
        return "user_change_password";
    }

    /**
     * Handles password change form submission.
     *
     * @param changePasswordDto    DTO carrying new password data
     * @param bindingResult        result of validation
     * @param redirectAttributes   for passing flash messages
     * @return the view to be rendered or redirect URL
     */
    @PostMapping("/change_password")
    public String changePassword(@Valid @ModelAttribute("user") ChangePasswordDto changePasswordDto,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "user_change_password";
        }

        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            memberService.changePassword(email, changePasswordDto.getNewPassword());
        } catch (IllegalStateException e) {
            bindingResult.rejectValue("password", "user.exists", e.getMessage());
            return "user_change_password";
        }

        redirectAttributes.addFlashAttribute("successMessage", "修改密碼成功");
        return "redirect:/";
    }
}
