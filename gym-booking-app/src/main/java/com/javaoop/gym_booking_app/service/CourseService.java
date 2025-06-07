package com.javaoop.gym_booking_app.service;

import com.javaoop.gym_booking_app.model.Course;
import com.javaoop.gym_booking_app.model.CourseStatus;
import com.javaoop.gym_booking_app.repository.CoachRepository;
import com.javaoop.gym_booking_app.repository.CourseRepository;
import com.javaoop.gym_booking_app.repository.GymRoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class CourseService {

    private final CourseRepository courseRepo;
    private final CoachRepository coachRepo;
    private final GymRoomRepository roomRepo;

    public CourseService(
        CourseRepository courseRepo,
        CoachRepository coachRepo,
        GymRoomRepository roomRepo
    ) {
        this.courseRepo  = courseRepo;
        this.coachRepo   = coachRepo;
        this.roomRepo    = roomRepo;
    }

    @Transactional(readOnly = true)
    public List<Course> getAllCourses() {
        return courseRepo.findAll();
    }

    public ServiceResult<Long> createCourse(
        Long coachId,
        Long roomId,
        String title,
        String description,
        Integer capacity,
        java.time.LocalDateTime startTime,
        java.time.LocalDateTime endTime,
        CourseStatus status,
        List<String> tags
    ) {
        var coachOpt = coachRepo.findById(coachId);
        if (coachOpt.isEmpty()) return ServiceResult.fail("教練不存在");
        var roomOpt  = roomRepo.findById(roomId);
        if (roomOpt.isEmpty()) return ServiceResult.fail("教室不存在");

        Course c = new Course();
        c.setCoach(coachOpt.get());
        c.setRoom(roomOpt.get());
        c.setTitle(title);
        c.setDescription(description);
        c.setCapacity(capacity);
        c.setStartTime(startTime);
        c.setEndTime(endTime);
        c.setStatus(status);
        c.setTags(new HashSet<>(tags));
        courseRepo.save(c);
        return ServiceResult.ok(c.getId());
    }
}