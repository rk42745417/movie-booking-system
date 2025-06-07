package com.javaoop.gym_booking_app.controller;

import com.javaoop.gym_booking_app.model.*;
import com.javaoop.gym_booking_app.service.AdminService;
import com.javaoop.gym_booking_app.service.ServiceResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /* ---------- 調整課程狀態 ---------- */
    @PatchMapping("/courses/{courseId}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long courseId,
                                          @RequestBody StatusReq req) {
        ServiceResult<Long> rs = adminService.updateCourseStatus(courseId, req.status());
        return rs.isSuccess() ? ResponseEntity.ok(rs) : ResponseEntity.badRequest().body(rs);
    }

    /* ---------- 取消課程（並同步取消預約） ---------- */
    @PostMapping("/courses/{courseId}/cancel")
    public ResponseEntity<?> cancelCourse(@PathVariable Long courseId,
                                          @RequestBody CancelReq req) {
        ServiceResult<Long> rs = adminService.cancelCourse(courseId, req.reason());
        return rs.isSuccess() ? ResponseEntity.ok(rs) : ResponseEntity.badRequest().body(rs);
    }

    /* ---------- 列出會員 ---------- */
    @GetMapping("/members")
    public List<Member> members() {
        return adminService.listMembers();
    }

    /* ---------- 依預約狀態列出預約 ---------- */
    @GetMapping("/reservations")
    public List<Reservation> reservations(@RequestParam ReservationStatus status) {
        return adminService.listReservationsByStatus(status);
    }

    /* DTO */
    public record StatusReq(CourseStatus status) {}
    public record CancelReq(String reason) {}
}