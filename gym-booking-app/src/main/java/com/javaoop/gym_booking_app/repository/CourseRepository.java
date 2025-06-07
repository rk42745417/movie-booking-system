package com.javaoop.gym_booking_app.repository;

import com.javaoop.gym_booking_app.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    // Example of a custom query: find all courses within a given time range
    List<Course> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);

    // Find all courses taught by a specific coach
    List<Course> findByCoachId(Long coachId);
}