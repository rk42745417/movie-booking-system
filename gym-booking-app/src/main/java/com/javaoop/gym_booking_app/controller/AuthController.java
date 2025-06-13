package com.javaoop.gym_booking_app.controller;

import com.javaoop.gym_booking_app.model.Member;
import com.javaoop.gym_booking_app.model.Gender;
import com.javaoop.gym_booking_app.service.MemberService;
import com.javaoop.gym_booking_app.service.ServiceResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller for handling authentication.
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final MemberService memberService;

    /**
     * Constructs an AuthController with the given MemberService.
     * @param memberService The service for handling member logic.
     */
    public AuthController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * Registers a new member.
     * @param req The request body containing the registration details.
     * @return A ResponseEntity with the result of the operation.
     */
    @PostMapping("/register")
    public ResponseEntity<ServiceResult<Long>> register(@RequestBody RegisterRequest req) {
        ServiceResult<Long> rs = memberService.register(
                req.email(), req.password(), req.fullName(),
                req.dateOfBirth(), req.gender(), req.phone());
        return rs.isSuccess()
                ? ResponseEntity.ok(rs)
                : ResponseEntity.badRequest().body(rs);
    }

    /**
     * Logs in a member.
     * @param req The request body containing the login details.
     * @return A ResponseEntity with the result of the operation.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        ServiceResult<Member> rs = memberService.loginAsResult(req.email(), req.password());
        if (rs.isSuccess() && rs.getData() != null) {
            // Use email as token for simplicity
            Map<String, Object> result = new HashMap<>();
            result.put("token", rs.getData().getEmail());
            result.put("member", rs.getData());
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(401).body(
                    Map.of("message", rs.getMessage() != null ? rs.getMessage() : "Login failed")
            );
        }
    }


    /**
     * DTO for a registration request.
     */
    public record RegisterRequest(
            String email,
            String password,
            String fullName,
            String dateOfBirth,
            Gender gender,
            String phone) {}

    /**
     * DTO for a login request.
     */
    public record LoginRequest(String email, String password) {}
}