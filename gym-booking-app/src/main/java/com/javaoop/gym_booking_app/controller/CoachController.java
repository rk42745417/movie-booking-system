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

    /** 取得所有課程（或改成 getCoursesByCoach(coachId)） */
    @GetMapping("/courses")
    public ResponseEntity<List<Course>> listCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }
}