package com.javaoop.gym_booking_app.service;

import com.javaoop.gym_booking_app.model.*;
import com.javaoop.gym_booking_app.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AdminService {

    private final CourseRepository courseRepository;
    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;

    /* ---------- Constructor Injection ---------- */
    public AdminService(CourseRepository courseRepository,
                        ReservationRepository reservationRepository,
                        MemberRepository memberRepository) {
        this.courseRepository      = courseRepository;
        this.reservationRepository = reservationRepository;
        this.memberRepository      = memberRepository;
    }

    /* 調整課程狀態 ------------------------------------------------------- */
    public ServiceResult<Long> updateCourseStatus(Long courseId, CourseStatus status) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            return ServiceResult.fail("課程不存在");
        }
        course.setStatus(status);
        courseRepository.save(course);
        return ServiceResult.ok(courseId);
    }

    /* 取消整堂課，並同步取消已預約的記錄 ------------------------------- */
    public ServiceResult<Long> cancelCourse(Long courseId, String reason) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            return ServiceResult.fail("課程不存在");
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

    /* 列出所有會員 ------------------------------------------------------- */
    @Transactional(readOnly = true)
    public List<Member> listMembers() {
        return memberRepository.findAll();
    }

    /* 依預約狀態列出預約 -------------------------------------------------- */
    @Transactional(readOnly = true)
    public List<Reservation> listReservationsByStatus(ReservationStatus status) {
        return reservationRepository.findAll().stream()
                                    .filter(r -> r.getStatus() == status)
                                    .toList();
    }
}