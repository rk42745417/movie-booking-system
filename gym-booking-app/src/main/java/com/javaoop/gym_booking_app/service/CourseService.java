package com.javaoop.gym_booking_app.service;

import com.javaoop.gym_booking_app.model.*;
import com.javaoop.gym_booking_app.repository.CoachRepository;
import com.javaoop.gym_booking_app.repository.CourseRepository;
import com.javaoop.gym_booking_app.repository.GymRoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.javaoop.gym_booking_app.repository.ReservationRepository;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class CourseService {
    private final ReservationRepository reservationRepo;
    private final CourseRepository courseRepo;
    private final CoachRepository coachRepo;
    private final GymRoomRepository roomRepo;

    /* ---------- 建構子注入 ---------- */


    public CourseService(
            CourseRepository courseRepo,
            CoachRepository coachRepo,
            GymRoomRepository roomRepo,
            ReservationRepository reservationRepo
    ) {
        this.courseRepo = courseRepo;
        this.coachRepo  = coachRepo;
        this.roomRepo   = roomRepo;
        this.reservationRepo = reservationRepo; 
    }

    /* ---------- 查詢 ---------- */
    @Transactional(readOnly = true)
    public List<Course> getAllCourses() {
        List<Course> list = courseRepo.findAll();
        for (Course c : list) {
            // 查詢這個課程有幾個 Reservation
            int count = reservationRepo.countByCourseId(c.getId());
            c.setReservedCount(count);  // 設進 Course
        }
        return list;
    }

    public List<Course> getAllOpenCourses() {
        List<Course> courseList = courseRepo.findByStatus(CourseStatus.OPEN);
        for (Course c : courseList) {
            int count = reservationRepo.countByCourseId(c.getId());
            c.setReservedCount(count);
        }
        return courseList;
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
    /* ------------ 只回傳需要給前端的欄位 ------------ */
    public record CourseSummary(
            Long id,
            String title,
            LocalDateTime startTime,
            LocalDateTime endTime,
            Integer capacity,
            CourseStatus status
    ) {}

    @Transactional(readOnly = true)
    public List<CourseSummary> upcoming(LocalDateTime from) {
        return courseRepo.findAll().stream()
                .filter(c -> c.getStartTime().isAfter(from))
                .map(c -> new CourseSummary(
                        c.getId(),
                        c.getTitle(),
                        c.getStartTime(),
                        c.getEndTime(),
                        c.getCapacity(),
                        c.getStatus()))
                .toList();
    }

    @Transactional
    public ServiceResult<Long> updateStatus(Long id, CourseStatus status) {
        if (courseRepo.findById(id).isEmpty()) {
            return ServiceResult.fail("course id not found");
        }
        Course course = courseRepo.findById(id).get();
        course.setStatus(status);
        courseRepo.save(course);
        return ServiceResult.ok(id);
    }





    
}