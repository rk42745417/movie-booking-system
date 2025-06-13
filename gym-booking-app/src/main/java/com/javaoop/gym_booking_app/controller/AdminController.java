package com.javaoop.gym_booking_app.controller;

import com.javaoop.gym_booking_app.model.*;
import com.javaoop.gym_booking_app.service.AdminService;
import com.javaoop.gym_booking_app.service.ServiceResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling administrative tasks.
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    /**
     * Constructs an AdminController with the given AdminService.
     * @param adminService The service for handling admin logic.
     */
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * Updates the status of a course.
     * @param courseId The ID of the course to update.
     * @param req The request body containing the new status.
     * @return A ResponseEntity with the result of the operation.
     */
    @PatchMapping("/courses/{courseId}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long courseId,
                                          @RequestBody StatusReq req) {
        ServiceResult<Long> rs = adminService.updateCourseStatus(courseId, req.status());
        return rs.isSuccess() ? ResponseEntity.ok(rs) : ResponseEntity.badRequest().body(rs);
    }

    /**
     * Cancels a course and all its reservations.
     * @param courseId The ID of the course to cancel.
     * @param req The request body containing the reason for cancellation.
     * @return A ResponseEntity with the result of the operation.
     */
    @PostMapping("/courses/{courseId}/cancel")
    public ResponseEntity<?> cancelCourse(@PathVariable Long courseId,
                                          @RequestBody CancelReq req) {
        ServiceResult<Long> rs = adminService.cancelCourse(courseId, req.reason());
        return rs.isSuccess() ? ResponseEntity.ok(rs) : ResponseEntity.badRequest().body(rs);
    }

    /**
     * Lists all members.
     * @return A list of all members.
     */
    @GetMapping("/members")
    public List<Member> members() {
        return adminService.listMembers();
    }

    /**
     * Lists reservations by their status.
     * @param status The status of the reservations to list.
     * @return A list of reservations with the given status.
     */
    @GetMapping("/reservations")
    public List<Reservation> reservations(@RequestParam ReservationStatus status) {
        return adminService.listReservationsByStatus(status);
    }

    /**
     * DTO for a status update request.
     * @param status The new status.
     */
    public record StatusReq(CourseStatus status) {}

    /**
     * DTO for a cancellation request.
     * @param reason The reason for cancellation.
     */
    public record CancelReq(String reason) {}
}