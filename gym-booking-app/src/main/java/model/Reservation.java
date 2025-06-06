package com.gymreservation.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations",
       uniqueConstraints = @UniqueConstraint(columnNames = {"booking_id", "member_id"}))
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime reservedAt;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    private String cancelReason;
}
