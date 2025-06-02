package com.gymreservation.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "gym_rooms")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class GymRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;          // A教室、B教室…

    private Integer capacity;     // 容量上限

    private String location;      // 樓層或場館位置
}
