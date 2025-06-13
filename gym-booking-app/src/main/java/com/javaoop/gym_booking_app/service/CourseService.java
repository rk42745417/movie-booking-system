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

/**
 * Service for handling courses.
 */
@Service
@Transactional
public class CourseService {
    private final ReservationRepository reservationRepo;
    private final CourseRepository courseRepo;
    private final CoachRepository coachRepo;
    private final GymRoomRepository roomRepo;

    /**
     * Constructs a CourseService with the given repositories.
     * @param courseRepo The repository for handling course data.
     * @param coachRepo The repository for handling coach data.
     * @param roomRepo The repository for handling gym room data.
     * @param reservationRepo The repository for handling reservation data.
     */
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

    /**
     * Gets all courses.
     * @return A list of all courses.
     */
    @Transactional(readOnly = true)
    public List<Course> getAllCourses() {
        List<Course> list = courseRepo.findAll();
        for (Course c : list) {
            // Find out how many reservations this course has
            int count = reservationRepo.countByCourseId(c.getId());
            c.setReservedCount(count);  // Set it in the Course object
        }
        return list;
    }

    /**
     * Gets all open courses.
     * @return A list of all open courses.
     */
    public List<Course> getAllOpenCourses() {
        List<Course> courseList = courseRepo.findByStatus(CourseStatus.OPEN);
        for (Course c : courseList) {
            int count = reservationRepo.countByCourseId(c.getId());
            c.setReservedCount(count);
        }
        return courseList;
    }

    /**
     * Creates a new course.
     * @param title The title of the new course.
     * @param description The description of the new course.
     * @param capacity The capacity of the new course.
     * @param startTime The start time of the new course.
     * @param endTime The end time of the new course.
     * @param status The status of the new course.
     * @param tags The tags of the new course.
     * @return A ServiceResult with the result of the operation.
     */
    public ServiceResult<Long> createCourse(
            String title,
            String description,
            Integer capacity,
            LocalDateTime startTime,
            LocalDateTime endTime,
            CourseStatus status,
            List<String> tags
    ) {

        /* Get (or create) a default coach ----------------------------------- */
        Coach coach = coachRepo.findAll()
                .stream()
                .findFirst()
                .orElseGet(() -> {
                    Coach c = new Coach();
                    c.setName("System Coach");
                    return coachRepo.save(c);
                });

        /* Get (or create) a default room ----------------------------------- */
        GymRoom room = roomRepo.findAll()
                .stream()
                .findFirst()
                .orElseGet(() -> {
                    GymRoom r = new GymRoom();
                    r.setName("Default Room");
                    r.setCapacity(30);
                    return roomRepo.save(r);
                });

        /* Create and save the course ---------------------------------------------- */
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
    /**
     * DTO for a course summary.
     */
    public record CourseSummary(
            Long id,
            String title,
            LocalDateTime startTime,
            LocalDateTime endTime,
            Integer capacity,
            CourseStatus status
    ) {}

    /**
     * Gets upcoming courses.
     * @param from The time to search after.
     * @return A list of upcoming courses.
     */
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

    /**
     * Updates the status of a course.
     * @param id The ID of the course to update.
     * @param status The new status of the course.
     * @return A ServiceResult with the result of the operation.
     */
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