package com.javaoop.gym_booking_app.controller;

import com.javaoop.gym_booking_app.model.Course;
import com.javaoop.gym_booking_app.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling coach-related tasks.
 */
@RestController
@RequestMapping("/api/v1/coach")
public class CoachController {

    private final CourseService courseService;

    /**
     * Constructs a CoachController with the given CourseService.
     * @param courseService The service for handling course logic.
     */
    public CoachController(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * Lists all courses.
     * @return A ResponseEntity with a list of all courses.
     */
    @GetMapping("/courses")
    public ResponseEntity<List<Course>> listCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    /**
     * Lists upcoming courses for the dashboard.
     * @return A ResponseEntity with a list of upcoming courses.
     */
    @GetMapping("/courses/upcoming")
    public ResponseEntity<List<CourseService.CourseSummary>> upcoming() {
        return ResponseEntity.ok(
                courseService.upcoming(java.time.LocalDateTime.now()));
    }
}