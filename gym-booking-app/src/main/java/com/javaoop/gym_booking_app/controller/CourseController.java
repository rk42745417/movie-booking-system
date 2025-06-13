package com.javaoop.gym_booking_app.controller;

import com.javaoop.gym_booking_app.model.CourseStatus;
import com.javaoop.gym_booking_app.service.CourseService;
import com.javaoop.gym_booking_app.service.ServiceResult;
import dto.CourseRequest;
import com.javaoop.gym_booking_app.model.Course;
import dto.UpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling courses.
 */
@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    private final CourseService courseService;

    /**
     * Constructs a CourseController with the given CourseService.
     * @param courseService The service for handling course logic.
     */
    public CourseController(CourseService courseService) { this.courseService = courseService; }


    /**
     * Gets all open courses.
     * @return A ResponseEntity with a list of all open courses.
     */
    @GetMapping("/open")
    public ResponseEntity<List<Course>> openCourses() {
        // Example returns all courses, can be changed to only return status = OPEN
        return ResponseEntity.ok(courseService.getAllOpenCourses());
    }


    /**
     * Gets all courses.
     * @return A ResponseEntity with a list of all courses.
     */
    @GetMapping
    public ResponseEntity<List<Course>> all() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    /**
     * Creates a new course.
     * @param req The request body containing the new course details.
     * @return A ResponseEntity with the result of the operation.
     */
    @PostMapping
    public ResponseEntity<ServiceResult<Long>> create(@RequestBody CourseRequest req) {
        var rs = courseService.createCourse(
                req.title(), req.description(), req.capacity(),
                req.startTime(), req.endTime(), req.status(), req.tags());
        return rs.isSuccess() ? ResponseEntity.ok(rs)
                : ResponseEntity.badRequest().body(rs);
    }

    /**
     * Updates the status of a course.
     * @param req The request body containing the new status.
     * @return A ResponseEntity with the result of the operation.
     */
    @PostMapping("/update")
    public ResponseEntity<ServiceResult<Long>> updateStatus(@RequestBody UpdateRequest req) {
        var rs = courseService.updateStatus(req.id(), req.status());
        return rs.isSuccess() ? ResponseEntity.ok(rs)
                : ResponseEntity.badRequest().body(rs);
    }

    /**
     * Lists upcoming courses.
     * @return A ResponseEntity with a list of upcoming courses.
     */
    @GetMapping("/courses/upcoming")
    public ResponseEntity<List<CourseService.CourseSummary>> upcoming() {
        return ResponseEntity.ok(
                courseService.upcoming(java.time.LocalDateTime.now()));
    }
}