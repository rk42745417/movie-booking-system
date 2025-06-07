package com.javaoop.gym_booking_app.controller;

import com.javaoop.gym_booking_app.model.Reservation;
import com.javaoop.gym_booking_app.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * 回傳某會員的所有預約清單。
     */
    @GetMapping("/{memberId}/reservations")
    public ResponseEntity<List<Reservation>> getReservations(
            @PathVariable Long memberId) {
        List<Reservation> list = memberService.getMemberReservations(memberId);
        return ResponseEntity.ok(list);
    }
}