package com.javaoop.gym_booking_app.controller;

import com.javaoop.gym_booking_app.model.Course;
import com.javaoop.gym_booking_app.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/coach")
public class CoachController {

    private final CourseService courseService;

    public CoachController(CourseService courseService) {
        this.courseService = courseService;
    }

    /** ➊ 仍保留列出全部（如果你還需要） */
    @GetMapping("/courses")
    public ResponseEntity<List<Course>> listCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    /** ➋ 給前端「儀表板」用的即將開課清單 */
    @GetMapping("/courses/upcoming")
    public ResponseEntity<List<CourseService.CourseSummary>> upcoming() {
        return ResponseEntity.ok(
                courseService.upcoming(java.time.LocalDateTime.now()));
    }
}