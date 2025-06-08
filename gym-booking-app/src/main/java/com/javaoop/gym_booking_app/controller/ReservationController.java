package com.javaoop.gym_booking_app.controller;

import com.javaoop.gym_booking_app.model.Member;
import com.javaoop.gym_booking_app.model.Course;
import com.javaoop.gym_booking_app.model.Reservation;
import com.javaoop.gym_booking_app.model.ReservationStatus;
import com.javaoop.gym_booking_app.repository.MemberRepository;
import com.javaoop.gym_booking_app.repository.CourseRepository;
import com.javaoop.gym_booking_app.repository.ReservationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final CourseRepository courseRepository;

    public ReservationController(
        ReservationRepository reservationRepository,
        MemberRepository memberRepository,
        CourseRepository courseRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.memberRepository = memberRepository;
        this.courseRepository = courseRepository;
    }

    // 取得「我的預約」
    @GetMapping("/my")
    public ResponseEntity<List<Reservation>> getMyReservations(@RequestHeader("Authorization") String token) {
        String email = extractEmailFromToken(token);
        Member member = memberRepository.findByEmail(email).orElse(null);
        if (member == null) {
            return ResponseEntity.status(401).build();
        }
        List<Reservation> list = reservationRepository.findByMemberId(member.getId());
        return ResponseEntity.ok(list);
    }

    // 預約課程（前端只需傳 { "courseId": 123 }）
    @PostMapping
    public ResponseEntity<?> create(@RequestBody ReservationRequest req, @RequestHeader("Authorization") String token) {
        // === 這裡加 debug log ===
        System.out.println("==== 收到報名請求 ====");
        System.out.println("token = " + token);
        System.out.println("extractEmailFromToken = " + extractEmailFromToken(token));

        String email = extractEmailFromToken(token);
        Member member = memberRepository.findByEmail(email).orElse(null);
        if (member == null) {
            System.out.println("找不到會員！");
            return ResponseEntity.status(401).body("尚未登入");
        }

        Course course = courseRepository.findById(req.getCourseId()).orElse(null);
        if (course == null) {
            System.out.println("找不到課程！");
            return ResponseEntity.badRequest().body("找不到課程");
        }

        Reservation r = new Reservation();
        r.setMember(member);
        r.setCourse(course);  // 關鍵！一定要設這個
        r.setStatus(ReservationStatus.RESERVED); // 預設狀態

        Reservation saved = reservationRepository.save(r);
        System.out.println("預約成功：" + saved.getId());
        return ResponseEntity.ok(saved);
    }

    // 取消預約
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancel(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        String email = extractEmailFromToken(token);
        Member member = memberRepository.findByEmail(email).orElse(null);
        if (member == null) {
            return ResponseEntity.status(401).build();
        }
        Reservation r = reservationRepository.findById(id).orElse(null);
        if (r == null || !r.getMember().getId().equals(member.getId())) {
            return ResponseEntity.status(403).build();
        }
        reservationRepository.delete(r);
        return ResponseEntity.ok().build();
    }

    // --- helper ---
    private String extractEmailFromToken(String token) {
        if (token == null || !token.startsWith("Bearer ")) return null;
        return token.substring(7);
    }

    // --- DTO ---
    public static class ReservationRequest {
        private Long courseId;
        public Long getCourseId() { return courseId; }
        public void setCourseId(Long courseId) { this.courseId = courseId; }
    }
}
