package com.javaoop.gym_booking_app.service;

import model.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 課程預約服務（純 Java 暫存版）
 *
 * 功能：
 *  1. reserveCourse(...)   ─ 預約課程
 *  2. cancelReservation(...) ─ 取消預約（開課前 30 分鐘以外）
 *  3. getMemberReservations(...) ─ 查詢會員所有預約
 *  4. getOpenCourses() ─ 查詢目前可報名課程清單
 */
public class ReservationService {

    /* ---------- 1. 預約課程 ---------- */
    public ReservationResult reserveCourse(Long memberId, Long bookingId) {

        Member  member  = Database.members.get(memberId);
        Booking booking = Database.bookings.get(bookingId);

        if (member == null)  return ReservationResult.fail("找不到會員");
        if (booking == null) return ReservationResult.fail("找不到課程");
        if (booking.getStatus() != CourseStatus.OPEN)
            return ReservationResult.fail("課程不在可預約狀態");

        /* 容量檢查 */
        long reservedCount = Database.reservations.values().stream()
                .filter(r -> r.getBooking().getId().equals(bookingId) &&
                             r.getStatus() == ReservationStatus.RESERVED)
                .count();
        if (reservedCount >= booking.getCapacity())
            return ReservationResult.fail("課程已額滿");

        /* （選擇性）年齡檢查：假設 Booking 內有 minAge 欄位 */
        if (booking.getMinAge() != null &&
            member.getDateOfBirth() != null) {

            int age = member.getDateOfBirth()
                            .until(LocalDateTime.now().toLocalDate())
                            .getYears();
            if (age < booking.getMinAge())
                return ReservationResult.fail("年齡未達課程限制");
        }

        /* 生成預約紀錄 */
        Long reservationId = Database.idSeq.incrementAndGet();
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setBooking(booking);
        reservation.setMember(member);
        reservation.setReservedAt(LocalDateTime.now());
        reservation.setStatus(ReservationStatus.RESERVED);

        Database.reservations.put(reservationId, reservation);
        return ReservationResult.success(reservationId);
    }

    /* ---------- 2. 取消預約 ---------- */
    public ReservationResult cancelReservation(Long reservationId, Long memberId) {

        Reservation r = Database.reservations.get(reservationId);
        if (r == null)                        return ReservationResult.fail("無此預約");
        if (!r.getMember().getId().equals(memberId))
            return ReservationResult.fail("無權取消此預約");
        if (r.getStatus() == ReservationStatus.CANCELLED)
            return ReservationResult.fail("預約已取消");

        Booking booking = r.getBooking();
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(booking.getStartTime().minusMinutes(30)))
            return ReservationResult.fail("距離開課不足 30 分鐘，無法取消");

        r.setStatus(ReservationStatus.CANCELLED);
        r.setCancelReason("使用者自行取消");

        return ReservationResult.success(reservationId);
    }

    /* ---------- 3. 查詢會員預約紀錄 ---------- */
    public List<Reservation> getMemberReservations(Long memberId) {
        return Database.reservations.values().stream()
                .filter(r -> r.getMember().getId().equals(memberId))
                .collect(Collectors.toList());
    }

    /* ---------- 4. 查詢目前可預約課程清單 ---------- */
    public List<Booking> getOpenCourses() {
        LocalDateTime now = LocalDateTime.now();
        return Database.bookings.values().stream()
                .filter(b -> b.getStatus() == CourseStatus.OPEN &&
                             now.isBefore(b.getStartTime()))
                .collect(Collectors.toList());
    }
}

/* ====== 回傳用簡易 DTO ====== */
class ReservationResult {
    private final boolean success;
    private final String  message;
    private final Long    reservationId;

    private ReservationResult(boolean success, String message, Long id) {
        this.success = success;
        this.message = message;
        this.reservationId = id;
    }

    public static ReservationResult success(Long id) { return new ReservationResult(true, null, id); }
    public static ReservationResult fail(String msg) { return new ReservationResult(false, msg, null); }


}