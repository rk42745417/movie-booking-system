package com.javaoop.gym_booking_app.service;

import com.javaoop.gym_booking_app.model.*;
import com.javaoop.gym_booking_app.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 使用者端課程服務：預約、取消、列出開放課程。
 */
@Service
@RequiredArgsConstructor
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;
    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;

    /* -------- 預約課程 -------- */
    public ServiceResult reserveCourse(Long memberId, Long courseId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        Course course = courseRepository.findById(courseId).orElse(null);
        if (member == null)  return ServiceResult.fail("會員不存在");
        if (course == null)  return ServiceResult.fail("課程不存在");
        if (course.getStatus() != CourseStatus.OPEN)
            return ServiceResult.fail("課程未開放預約");

        long reservedCount = reservationRepository.countByCourseIdAndStatus(courseId, ReservationStatus.RESERVED);
        if (reservedCount >= course.getCapacity())
            return ServiceResult.fail("名額已滿");

        if (reservationRepository.findByMemberId(memberId).stream()
                .anyMatch(r -> r.getCourse().getId().equals(courseId) && r.getStatus() == ReservationStatus.RESERVED))
            return ServiceResult.fail("已預約此課程");

        Reservation r = new Reservation();
        r.setMember(member);
        r.setCourse(course);
        r.setReservedAt(LocalDateTime.now());
        r.setStatus(ReservationStatus.RESERVED);
        r = reservationRepository.save(r);
        return ServiceResult.success(r.getId());
    }

    /* -------- 取消預約 -------- */
    public ServiceResult cancelReservation(Long reservationId, Long memberId) {
        Reservation r = reservationRepository.findById(reservationId).orElse(null);
        if (r == null) return ServiceResult.fail("預約不存在");
        if (!r.getMember().getId().equals(memberId))
            return ServiceResult.fail("無權取消");
        if (r.getStatus() != ReservationStatus.RESERVED)
            return ServiceResult.fail("狀態不可取消");
        if (LocalDateTime.now().isAfter(r.getCourse().getStartTime().minusMinutes(30)))
            return ServiceResult.fail("距離開課不足 30 分鐘無法取消");

        r.setStatus(ReservationStatus.CANCELED);
        r.setCancelledAt(LocalDateTime.now());
        r.setCancelReason("USER_CANCEL");
        reservationRepository.save(r);
        return ServiceResult.success(reservationId);
    }

    /* -------- 查詢開放課程 -------- */
    @Transactional(readOnly = true)
    public List<Course> listOpenCourses() {
        LocalDateTime now = LocalDateTime.now();
        return courseRepository.findAll().stream()
                .filter(c -> c.getStatus() == CourseStatus.OPEN && now.isBefore(c.getStartTime()))
                .toList();
    }
}