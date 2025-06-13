package com.javaoop.gym_booking_app.service;

import com.javaoop.gym_booking_app.model.*;
import com.javaoop.gym_booking_app.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for handling administrative tasks.
 */
@Service
@Transactional
public class AdminService {

    private final CourseRepository courseRepository;
    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;

    /**
     * Constructs an AdminService with the given repositories.
     * @param courseRepository The repository for handling course data.
     * @param reservationRepository The repository for handling reservation data.
     * @param memberRepository The repository for handling member data.
     */
    public AdminService(CourseRepository courseRepository,
                        ReservationRepository reservationRepository,
                        MemberRepository memberRepository) {
        this.courseRepository      = courseRepository;
        this.reservationRepository = reservationRepository;
        this.memberRepository      = memberRepository;
    }

    /**
     * Updates the status of a course.
     * @param courseId The ID of the course to update.
     * @param status The new status of the course.
     * @return A ServiceResult with the result of the operation.
     */
    public ServiceResult<Long> updateCourseStatus(Long courseId, CourseStatus status) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            return ServiceResult.fail("Course does not exist");
        }
        course.setStatus(status);
        courseRepository.save(course);
        return ServiceResult.ok(courseId);
    }

    /**
     * Cancels a course and all its reservations.
     * @param courseId The ID of the course to cancel.
     * @param reason The reason for cancellation.
     * @return A ServiceResult with the result of the operation.
     */
    public ServiceResult<Long> cancelCourse(Long courseId, String reason) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            return ServiceResult.fail("Course does not exist");
        }
        course.setStatus(CourseStatus.CANCELED);
        courseRepository.save(course);

        reservationRepository.findByCourseId(courseId).forEach(r -> {
            if (r.getStatus() == ReservationStatus.RESERVED) {
                r.setStatus(ReservationStatus.CANCELED);
                r.setCancelledAt(LocalDateTime.now());
                r.setCancelReason(reason);
                reservationRepository.save(r);
            }
        });
        return ServiceResult.ok(courseId);
    }

    /**
     * Lists all members.
     * @return A list of all members.
     */
    @Transactional(readOnly = true)
    public List<Member> listMembers() {
        return memberRepository.findAll();
    }

    /**
     * Lists reservations by their status.
     * @param status The status of the reservations to list.
     * @return A list of reservations with the given status.
     */
    @Transactional(readOnly = true)
    public List<Reservation> listReservationsByStatus(ReservationStatus status) {
        return reservationRepository.findAll().stream()
                .filter(r -> r.getStatus() == status)
                .toList();
    }
}