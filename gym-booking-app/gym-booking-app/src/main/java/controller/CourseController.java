/**
 * 
 */
package controller;

import model.CourseStatus;
import service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 
 */
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/add")
    public CourseStatus addCourse(@RequestBody CourseStatus course) {
        return courseService.addCourse(course);
    }

    @GetMapping("/byCoach/{coachId}")
    public List<CourseStatus> getCoursesByCoach(@PathVariable Long coachId) {
        return courseService.getCoursesByCoach(coachId);
    }

}
