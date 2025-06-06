package com.gymreservation.model;   // ★跟主程式相同最安全

import jakarta.persistence.*;        // Boot 3.x 用 jakarta

@Entity
@Table(name = "coaches")
public class Coach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;
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
    private String phone;

    /** 教練專長，如 TRX、瑜伽… */
    private String specialization;

    @Column(columnDefinition = "TEXT")
    private String bio;

    /* -------- 無參建構子 (JPA 規定) -------- */
    public Coach() {}

    /* -------- 全參建構子 -------- */
    public Coach(String name, String email, String phone,
                 String specialization, String bio) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.specialization = specialization;
        this.bio = bio;
    }

    /* -------- Getter／Setter -------- */
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
}