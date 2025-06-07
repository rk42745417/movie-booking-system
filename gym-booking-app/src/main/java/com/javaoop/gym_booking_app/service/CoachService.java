package com.javaoop.gym_booking_app.service;

import com.javaoop.gym_booking_app.model.Course;
import com.javaoop.gym_booking_app.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CoachService {

    private final CourseRepository courseRepository;

    public CoachService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    /** 列出某教練自己所有課程 */
    public List<Course> getCoursesByCoach(Long coachId) {
        return courseRepository.findByCoachId(coachId);
    }
}