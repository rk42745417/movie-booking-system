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

/**
 * Controller for handling reservations.
 */
@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final CourseRepository courseRepository;

    /**
     * Constructs a ReservationController with the given repositories.
     * @param reservationRepository The repository for handling reservation data.
     * @param memberRepository The repository for handling member data.
     * @param courseRepository The repository for handling course data.
     */
    public ReservationController(
            ReservationRepository reservationRepository,
            MemberRepository memberRepository,
            CourseRepository courseRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.memberRepository = memberRepository;
        this.courseRepository = courseRepository;
    }

    /**
     * Gets the reservations for the current member.
     * @param token The authentication token.
     * @return A ResponseEntity with a list of reservations for the current member.
     */
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

    /**
     * Creates a new reservation.
     * @param req The request body containing the reservation details.
     * @param token The authentication token.
     * @return A ResponseEntity with the result of the operation.
     */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody ReservationRequest req, @RequestHeader("Authorization") String token) {
        System.out.println("==== Received booking request ====");
        System.out.println("token = " + token);
        System.out.println("extractEmailFromToken = " + extractEmailFromToken(token));

        String email = extractEmailFromToken(token);
        Member member = memberRepository.findByEmail(email).orElse(null);
        if (member == null) {
            System.out.println("Member not found!");
            return ResponseEntity.status(401).body("Not logged in");
        }

        Course course = courseRepository.findById(req.getCourseId()).orElse(null);
        if (course == null) {
            System.out.println("Course not found!");
            return ResponseEntity.badRequest().body("Course not found");
        }

        Reservation r = new Reservation();
        r.setMember(member);
        r.setCourse(course);  // Critical! Must set this
        r.setStatus(ReservationStatus.RESERVED); // Default status

        Reservation saved = reservationRepository.save(r);
        System.out.println("Reservation successful: " + saved.getId());
        return ResponseEntity.ok(saved);
    }

    /**
     * Cancels a reservation.
     * @param id The ID of the reservation to cancel.
     * @param token The authentication token.
     * @return A ResponseEntity with the result of the operation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancel(@PathVariable("id") Long id, @RequestHeader("Authorization") String token) {
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

    /**
     * Extracts the email from the authentication token.
     * @param token The authentication token.
     * @return The email extracted from the token.
     */
    private String extractEmailFromToken(String token) {
        if (token == null || !token.startsWith("Bearer ")) return null;
        return token.substring(7);
    }

    /**
     * DTO for a reservation request.
     */
    public static class ReservationRequest {
        private Long courseId;
        public Long getCourseId() { return courseId; }
        public void setCourseId(Long courseId) { this.courseId = courseId; }
    }
}