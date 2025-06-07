package com.javaoop.gym_booking_app.controller;

import com.javaoop.gym_booking_app.model.Course;
import com.javaoop.gym_booking_app.service.CoachService;
import com.javaoop.gym_booking_app.service.ServiceResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/coaches")
public class CoachController {

    private final CoachService coachService;

    public CoachController(CoachService coachService) {
        this.coachService = coachService;
    }

    /* ---------- 建立課程 ---------- */
    @PostMapping("/{coachId}/courses")
    public ResponseEntity<?> createCourse(@PathVariable Long coachId,
                                          @RequestBody CreateCourseReq req) {
        ServiceResult<Long> rs = coachService.createCourse(
                coachId, req.title(), req.description(), req.roomId(), req.capacity(),
                req.startTime(), req.endTime());
        return rs.isSuccess() ? ResponseEntity.ok(rs) : ResponseEntity.badRequest().body(rs);
    }

    /* ---------- 列出教練課程 ---------- */
    @GetMapping("/{coachId}/courses")
    public List<Course> listCoachCourses(@PathVariable Long coachId) {
        return coachService.listCourses(coachId);
    }

    /* DTO */
    public record CreateCourseReq(String title, String description,
                                  Long roomId, Integer capacity,
                                  LocalDateTime startTime, LocalDateTime endTime) {}
}