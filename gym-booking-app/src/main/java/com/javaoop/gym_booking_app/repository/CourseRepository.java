package com.javaoop.gym_booking_app.repository;

import com.javaoop.gym_booking_app.model.Course;
import com.javaoop.gym_booking_app.model.CourseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for handling courses.
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    /**
     * Finds all courses for a specific coach.
     * @param coachId The ID of the coach.
     * @return A list of all courses for the specified coach.
     */
    List<Course> findByCoachId(Long coachId);

    /**
     * Finds all courses with a specific status.
     * @param courseStatus The status of the courses to find.
     * @return A list of all courses with the specified status.
     */
    List<Course> findByStatus(CourseStatus courseStatus);

    /**
     * Finds all courses that start after a specific time, ordered by start time.
     * @param from The time to search after.
     * @return A list of all courses that start after the specified time, ordered by start time.
     */
    List<Course> findByStartTimeAfterOrderByStartTimeAsc(LocalDateTime from);
}