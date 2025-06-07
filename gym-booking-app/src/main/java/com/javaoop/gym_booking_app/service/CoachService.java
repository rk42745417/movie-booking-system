package com.javaoop.gym_booking_app.service;

import com.javaoop.gym_booking_app.model.*;
import com.javaoop.gym_booking_app.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 教練端服務：建立／查詢自己的課程。
 */
@Service
@RequiredArgsConstructor
@Transactional
public class CoachService {

    private final CoachRepository coachRepository;
    private final GymRoomRepository roomRepository;
    private final CourseRepository courseRepository;

    /* -------- 教練建立課程 -------- */
    public ServiceResult createCourse(Long coachId, String title, String desc,
                                      Long roomId, Integer capacity,
                                      LocalDateTime start, LocalDateTime end) {
        Coach coach = coachRepository.findById(coachId).orElse(null);
        GymRoom room = roomRepository.findById(roomId).orElse(null);
        if (coach == null) return ServiceResult.fail("教練不存在");
        if (room == null)  return ServiceResult.fail("教室不存在");
        if (start.isAfter(end)) return ServiceResult.fail("開始時間必須早於結束時間");

        /* 檢查教室時間衝突 */
        boolean overlap = courseRepository.findByStartTimeBetween(start, end).stream()
                .anyMatch(c -> c.getRoom().getId().equals(roomId) && !c.getStatus().equals(CourseStatus.CANCELED));
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
        return ServiceResult.success(c.getId());
    }

    /* -------- 列出教練課程 -------- */
    @Transactional(readOnly = true)
    public List<Course> listCourses(Long coachId) {
        return courseRepository.findByCoachId(coachId);
    }
}