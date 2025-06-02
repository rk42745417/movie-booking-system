package com.gymreservation.model;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "bookings")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;                 // 課程名稱 (如：早晨瑜伽)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coach_id")
    private Coach coach;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_room_id")
    private GymRoom gymRoom;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private Integer capacity;            // 允許預約人數上限

    @Enumerated(EnumType.STRING)
    private CourseStatus status;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();
}