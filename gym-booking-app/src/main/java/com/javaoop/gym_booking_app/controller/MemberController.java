package com.javaoop.gym_booking_app.controller;

import com.javaoop.gym_booking_app.model.Reservation;
import com.javaoop.gym_booking_app.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling member-related tasks.
 */
@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    /**
     * Constructs a MemberController with the given MemberService.
     * @param memberService The service for handling member logic.
     */
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * Returns a list of all reservations for a member.
     * @param memberId The ID of the member.
     * @return A ResponseEntity with a list of all reservations for the member.
     */
    @GetMapping("/{memberId}/reservations")
    public ResponseEntity<List<Reservation>> getReservations(
            @PathVariable Long memberId) {
        List<Reservation> list = memberService.getMemberReservations(memberId);
        return ResponseEntity.ok(list);
    }
}