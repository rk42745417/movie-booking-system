package com.javaoop.gym_booking_app.controller;

import com.javaoop.gym_booking_app.model.Course;
import com.javaoop.gym_booking_app.service.CourseService;
import com.javaoop.gym_booking_app.service.ServiceResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    /* ---------- 查詢開放課程 ---------- */
    @GetMapping("/open")
    public List<Course> openCourses() {
        return courseService.listOpenCourses();
    }

    /* ---------- 預約課程 ---------- */
    @PostMapping("/{courseId}/reserve")
    public ResponseEntity<?> reserve(@PathVariable Long courseId, @RequestBody ReserveReq req) {
        ServiceResult<Long> rs = courseService.reserveCourse(req.memberId(), courseId);
        return rs.isSuccess() ? ResponseEntity.ok(rs) : ResponseEntity.badRequest().body(rs);
    }

    /* ---------- 取消預約 ---------- */
    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<?> cancel(@PathVariable Long reservationId,
                                    @RequestParam Long memberId) {
        ServiceResult<Long> rs = courseService.cancelReservation(reservationId, memberId);
        return rs.isSuccess() ? ResponseEntity.ok(rs) : ResponseEntity.badRequest().body(rs);
    }

    /* DTO */
    public record ReserveReq(Long memberId) {}
}