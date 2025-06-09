package com.javaoop.gym_booking_app.repository;

import com.javaoop.gym_booking_app.model.Course;
import com.javaoop.gym_booking_app.model.CourseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    /** 舊有：依教練查詢 */
    List<Course> findByCoachId(Long coachId);

    List<Course> findByStatus(CourseStatus courseStatus);

    /** 新增：查詢「某時間之後」的課程，依開始時間排序 */
    List<Course> findByStartTimeAfterOrderByStartTimeAsc(LocalDateTime from);
}