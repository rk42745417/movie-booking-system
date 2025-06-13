package com.javaoop.gym_booking_app.service;

import com.javaoop.gym_booking_app.model.Course;
import com.javaoop.gym_booking_app.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for handling coach-related tasks.
 */
@Service
@Transactional(readOnly = true)
public class CoachService {

    private final CourseRepository courseRepository;

    /**
     * Constructs a CoachService with the given CourseRepository.
     * @param courseRepository The repository for handling course data.
     */
    public CoachService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    /**
     * Lists all courses for a specific coach.
     * @param coachId The ID of the coach.
     * @return A list of all courses for the specified coach.
     */
    public List<Course> getCoursesByCoach(Long coachId) {
        return courseRepository.findByCoachId(coachId);
    }
}