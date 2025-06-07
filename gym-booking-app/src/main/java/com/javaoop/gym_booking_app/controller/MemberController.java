package com.javaoop.gym_booking_app.controller;

import com.javaoop.gym_booking_app.model.*;
import com.javaoop.gym_booking_app.service.MemberService;
import com.javaoop.gym_booking_app.service.ServiceResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /* ---------- 註冊 ---------- */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        ServiceResult<Long> rs = memberService.register(
                req.email(), req.password(), req.fullName(),
                req.dateOfBirth(), req.gender(), req.phone());
        return rs.isSuccess() ? ResponseEntity.ok(rs) : ResponseEntity.badRequest().body(rs);
    }
    
    

    /* ---------- 登入 ---------- */
    @PostMapping("/login")
    public ResponseEntity<Member> login(@RequestBody LoginRequest req) {
        Member m = memberService.login(req.email(), req.password());
        return m == null ? ResponseEntity.status(401).build() : ResponseEntity.ok(m);
    }

    /* ---------- 會員預約清單 ---------- */
    @GetMapping("/{memberId}/reservations")
    public List<Reservation> reservations(@PathVariable Long memberId) {
        return memberService.getMemberReservations(memberId);
    }

    /* --------- request DTO --------- */
    public record RegisterRequest(String email, String password, String fullName,
                                  String dateOfBirth, Gender gender, String phone) {}
    public record LoginRequest(String email, String password) {}
}