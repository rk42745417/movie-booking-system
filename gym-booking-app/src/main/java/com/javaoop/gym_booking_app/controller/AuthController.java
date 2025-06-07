package com.javaoop.gym_booking_app.controller;

import com.javaoop.gym_booking_app.model.Member;
import com.javaoop.gym_booking_app.model.Gender;
import com.javaoop.gym_booking_app.service.MemberService;
import com.javaoop.gym_booking_app.service.ServiceResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final MemberService memberService;

    public AuthController(MemberService memberService) {
        this.memberService = memberService;
    }

    /* ---------- 註冊 ---------- */
    @PostMapping("/register")
    public ResponseEntity<ServiceResult<Long>> register(@RequestBody RegisterRequest req) {
        ServiceResult<Long> rs = memberService.register(
                req.email(), req.password(), req.fullName(),
                req.dateOfBirth(), req.gender(), req.phone());
        return rs.isSuccess()
                ? ResponseEntity.ok(rs)
                : ResponseEntity.badRequest().body(rs);
    }

    /* ---------- 登入 ---------- */
    @PostMapping("/login")
    public ResponseEntity<ServiceResult<Member>> login(@RequestBody LoginRequest req) {
        ServiceResult<Member> rs = memberService.loginAsResult(req.email(), req.password());
        return rs.isSuccess()
                ? ResponseEntity.ok(rs)
                : ResponseEntity.status(401).body(rs);
    }

    /* --------- DTO --------- */
    public record RegisterRequest(
            String email,
            String password,
            String fullName,
            String dateOfBirth,
            Gender gender,
            String phone) {}

    public record LoginRequest(String email, String password) {}
}