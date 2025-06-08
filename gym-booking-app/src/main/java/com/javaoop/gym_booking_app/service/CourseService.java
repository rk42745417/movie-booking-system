package com.javaoop.gym_booking_app.service;

import com.javaoop.gym_booking_app.model.*;
import com.javaoop.gym_booking_app.repository.CoachRepository;
import com.javaoop.gym_booking_app.repository.CourseRepository;
import com.javaoop.gym_booking_app.repository.GymRoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class CourseService {

    private final CourseRepository courseRepo;
    private final CoachRepository coachRepo;
    private final GymRoomRepository roomRepo;

    /* ---------- 建構子注入 ---------- */
    public CourseService(
            CourseRepository courseRepo,
            CoachRepository coachRepo,
            GymRoomRepository roomRepo
    ) {
        this.courseRepo = courseRepo;
        this.coachRepo  = coachRepo;
        this.roomRepo   = roomRepo;
    }

    /* ---------- 查詢 ---------- */
    @Transactional(readOnly = true)
    public List<Course> getAllCourses() {
        return courseRepo.findAll();
    }

    /* ---------- 建立課程 ---------- */
    public ServiceResult<Long> createCourse(
            String title,
            String description,
            Integer capacity,
            LocalDateTime startTime,
            LocalDateTime endTime,
            CourseStatus status,
            List<String> tags
    ) {

        /* 取得（或自動建立）預設教練 ----------------------------------- */
        Coach coach = coachRepo.findAll()
                               .stream()
                               .findFirst()
                               .orElseGet(() -> {
                                   Coach c = new Coach();
                                   c.setName("系統教練");
                                   return coachRepo.save(c);
                               });

        /* 取得（或自動建立）預設教室 ----------------------------------- */
        GymRoom room = roomRepo.findAll()
                               .stream()
                               .findFirst()
                               .orElseGet(() -> {
                                   GymRoom r = new GymRoom();
                                   r.setName("Default Room");
                                   r.setCapacity(30);
                                   return roomRepo.save(r);
                               });

        /* 建立並儲存課程 ---------------------------------------------- */
        Course c = new Course();
        c.setCoach(coach);
        c.setRoom(room);
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