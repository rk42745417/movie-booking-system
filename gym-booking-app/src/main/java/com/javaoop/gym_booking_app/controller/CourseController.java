package com.javaoop.gym_booking_app.controller;

import dto.CourseRequest;
import com.javaoop.gym_booking_app.model.Course;
import com.javaoop.gym_booking_app.service.CourseService;
import com.javaoop.gym_booking_app.service.ServiceResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    private final CourseService courseService;
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<Course>> all() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @PostMapping
    public ResponseEntity<ServiceResult<Long>> create(
            @RequestBody CourseRequest req,
            @RequestHeader("Authorization") String authHeader
    ) {
        // TODO: 從 authHeader 拿到 coachId（例如 decode JWT）
        Long coachId = /* extractCoachId(authHeader) */ 1L;
        ServiceResult<Long> rs = courseService.createCourse(
            coachId,
            req.roomId(),
            req.title(),
            req.description(),
            req.capacity(),
            req.startTime(),
            req.endTime(),
            req.status(),
            req.tags()
        );
        return rs.isSuccess()
                ? ResponseEntity.ok(rs)
                : ResponseEntity.badRequest().body(rs);
    }
}