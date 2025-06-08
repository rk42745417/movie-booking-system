package com.javaoop.gym_booking_app.controller;

import com.javaoop.gym_booking_app.service.CourseService;
import com.javaoop.gym_booking_app.service.ServiceResult;
import dto.CourseRequest;
import com.javaoop.gym_booking_app.model.Course;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    private final CourseService courseService;
    public CourseController(CourseService courseService) { this.courseService = courseService; }


    // 取得所有開放中課程（你可依需求篩選 status）
    @GetMapping("/open")
    public ResponseEntity<List<Course>> openCourses() {
        // 範例只回傳所有課程，也可以改成只回傳 status = OPEN 的課程
        return ResponseEntity.ok(courseService.getAllCourses());
    }


    /* 取得全部課程 */
    @GetMapping
    public ResponseEntity<List<Course>> all() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    /* 新增課程 (已移除 coachName、roomId) */
    @PostMapping
    public ResponseEntity<ServiceResult<Long>> create(@RequestBody CourseRequest req) {
        var rs = courseService.createCourse(
                req.title(), req.description(), req.capacity(),
                req.startTime(), req.endTime(), req.status(), req.tags());
        return rs.isSuccess() ? ResponseEntity.ok(rs)
                              : ResponseEntity.badRequest().body(rs);
    }
    
    @GetMapping("/courses/upcoming")
    public ResponseEntity<List<CourseService.CourseSummary>> upcoming() {
        return ResponseEntity.ok(
                courseService.upcoming(java.time.LocalDateTime.now()));
    }
}