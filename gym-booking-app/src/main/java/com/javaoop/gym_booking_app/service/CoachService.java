package com.javaoop.gym_booking_app.service;

import com.javaoop.gym_booking_app.model.*;
import com.javaoop.gym_booking_app.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CoachService {

    private final CoachRepository coachRepository;
    private final GymRoomRepository roomRepository;
    private final CourseRepository courseRepository;

    public CoachService(CoachRepository coachRepository,
                        GymRoomRepository roomRepository,
                        CourseRepository courseRepository) {
        this.coachRepository = coachRepository;
        this.roomRepository = roomRepository;
        this.courseRepository = courseRepository;
    }

    public ServiceResult<Long> createCourse(Long coachId, String title, String desc,
                                            Long roomId, Integer capacity,
                                            LocalDateTime start, LocalDateTime end) {
        Coach coach = coachRepository.findById(coachId).orElse(null);
        GymRoom room = roomRepository.findById(roomId).orElse(null);
        if (coach == null) return ServiceResult.fail("教練不存在");
        if (room == null)  return ServiceResult.fail("教室不存在");
        if (start.isAfter(end)) return ServiceResult.fail("開始時間必須早於結束時間");

        boolean overlap = courseRepository.findByStartTimeBetween(start, end).stream()
                .anyMatch(c -> c.getRoom().getId().equals(roomId) && c.getStatus()!=CourseStatus.CANCELED);
        if (overlap) return ServiceResult.fail("時段與其他課程衝突");

        Course c = new Course();
        c.setTitle(title);
        c.setDescription(desc);
        c.setCoach(coach);
        c.setRoom(room);
        c.setCapacity(capacity);
        c.setStartTime(start);
        c.setEndTime(end);
        c.setStatus(CourseStatus.OPEN);
        c = courseRepository.save(c);
        return ServiceResult.ok(c.getId());
    }

    @Transactional(readOnly = true)
    public List<Course> listCourses(Long coachId) {
        return courseRepository.findByCoachId(coachId);
    }
}