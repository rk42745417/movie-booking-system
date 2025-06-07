package com.javaoop.gym_booking_app.service;

import com.javaoop.gym_booking_app.model.*;
import com.javaoop.gym_booking_app.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系統管理員功能：課程狀態管理、強制取消等。
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final CourseRepository courseRepository;
    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;

    /* -------- 調整課程狀態 -------- */
    public ServiceResult updateCourseStatus(Long courseId, CourseStatus status) {
        Course c = courseRepository.findById(courseId).orElse(null);
        if (c == null) return ServiceResult.fail("課程不存在");
        c.setStatus(status);
        courseRepository.save(c);
        return ServiceResult.success(courseId);
    }

    /* -------- 列出指定狀態的預約 -------- */
    @Transactional(readOnly = true)
    public List<Reservation> listReservationsByStatus(ReservationStatus status) {
        return reservationRepository.findAll().stream()
                .filter(r -> r.getStatus() == status)
                .toList();
    }

    /* -------- 取消整堂課程並同步取消預約 -------- */
    public ServiceResult cancelCourse(Long courseId, String reason) {
        Course c = courseRepository.findById(courseId).orElse(null);
        if (c == null) return ServiceResult.fail("課程不存在");
        c.setStatus(CourseStatus.CANCELED);
        courseRepository.save(c);

        reservationRepository.findByCourseId(courseId).forEach(r -> {
            if (r.getStatus() == ReservationStatus.RESERVED) {
                r.setStatus(ReservationStatus.CANCELED);
                r.setCancelledAt(LocalDateTime.now());
                r.setCancelReason(reason);
                reservationRepository.save(r);
            }
        });
        return ServiceResult.success(courseId);
    }

    /* -------- 列出所有會員 -------- */
    @Transactional(readOnly = true)
    public List<Member> listMembers() {
        return memberRepository.findAll();
    }
}