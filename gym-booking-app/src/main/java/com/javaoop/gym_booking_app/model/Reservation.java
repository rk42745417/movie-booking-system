package com.javaoop.gym_booking_app.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

/**
 * Represents a member's reservation for a specific course.
 */
@Entity
@Table(name = "reservation", uniqueConstraints = {
        // This ensures a member can only book a specific course once.
        @UniqueConstraint(columnNames = {"member_id", "course_id"})
})
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many reservations can be made by one member.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // Many reservations can exist for one course.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    // Automatically sets the timestamp when the reservation is created.
    @CreationTimestamp
    @Column(name = "reserved_at", nullable = false, updatable = false)
    private LocalDateTime reservedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReservationStatus status;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt; // Null if not canceled

    @Column(name = "cancel_reason")
    private String cancelReason; // Optional reason for cancellation


    /**
     * Default constructor.
     */
    public Reservation() {
    }


    /**
     * Gets the ID of the reservation.
     * @return The ID of the reservation.
     */
    public Long getId() { return id; }

    /**
     * Sets the ID of the reservation.
     * @param id The new ID of the reservation.
     */
    public void setId(Long id) { this.id = id; }

    /**
     * Gets the member of the reservation.
     * @return The member of the reservation.
     */
    public Member getMember() { return member; }

    /**
     * Sets the member of the reservation.
     * @param member The new member of the reservation.
     */
    public void setMember(Member member) { this.member = member; }

    /**
     * Gets the course of the reservation.
     * @return The course of the reservation.
     */
    public Course getCourse() { return course; }

    /**
     * Sets the course of the reservation.
     * @param course The new course of the reservation.
     */
    public void setCourse(Course course) { this.course = course; }

    /**
     * Gets the reservation time.
     * @return The reservation time.
     */
    public LocalDateTime getReservedAt() { return reservedAt; }

    /**
     * Sets the reservation time.
     * @param reservedAt The new reservation time.
     */
    public void setReservedAt(LocalDateTime reservedAt) { this.reservedAt = reservedAt; }

    /**
     * Gets the status of the reservation.
     * @return The status of the reservation.
     */
    public ReservationStatus getStatus() { return status; }

    /**
     * Sets the status of the reservation.
     * @param status The new status of the reservation.
     */
    public void setStatus(ReservationStatus status) { this.status = status; }

    /**
     * Gets the cancellation time of the reservation.
     * @return The cancellation time of the reservation.
     */
    public LocalDateTime getCancelledAt() { return cancelledAt; }

    /**
     * Sets the cancellation time of the reservation.
     * @param cancelledAt The new cancellation time of the reservation.
     */
    public void setCancelledAt(LocalDateTime cancelledAt) { this.cancelledAt = cancelledAt; }

    /**
     * Gets the cancellation reason of the reservation.
     * @return The cancellation reason of the reservation.
     */
    public String getCancelReason() { return cancelReason; }

    /**
     * Sets the cancellation reason of the reservation.
     * @param cancelReason The new cancellation reason of the reservation.
     */
    public void setCancelReason(String cancelReason) { this.cancelReason = cancelReason; }
}