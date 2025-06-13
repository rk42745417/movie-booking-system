package com.javaoop.gym_booking_app.model;

import com.javaoop.gym_booking_app.model.Gender;
import com.javaoop.gym_booking_app.model.Role;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

/**
 * Represents a member entity using Jakarta Persistence (JPA).
 */
@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "full_name", nullable = false, length = 50)
    private String fullName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "phone")
    private String phone; // Optional

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 10)
    private Role role;

    // Automatically sets the timestamp when the entity is first created.
    // NOTE: This uses a Hibernate annotation, which is standard in Spring Boot JPA.
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    /**
     * Default constructor.
     */
    public Member() {
    }

    /**
     * Gets the ID of the member.
     * @return The ID of the member.
     */
    public Long getId() { return id; }

    /**
     * Sets the ID of the member.
     * @param id The new ID of the member.
     */
    public void setId(Long id) { this.id = id; }

    /**
     * Gets the email of the member.
     * @return The email of the member.
     */
    public String getEmail() { return email; }

    /**
     * Sets the email of the member.
     * @param email The new email of the member.
     */
    public void setEmail(String email) { this.email = email; }

    /**
     * Gets the password hash of the member.
     * @return The password hash of the member.
     */
    public String getPasswordHash() { return passwordHash; }

    /**
     * Sets the password hash of the member.
     * @param passwordHash The new password hash of the member.
     */
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    /**
     * Gets the full name of the member.
     * @return The full name of the member.
     */
    public String getFullName() { return fullName; }

    /**
     * Sets the full name of the member.
     * @param fullName The new full name of the member.
     */
    public void setFullName(String fullName) { this.fullName = fullName; }

    /**
     * Gets the date of birth of the member.
     * @return The date of birth of the member.
     */
    public LocalDate getDateOfBirth() { return dateOfBirth; }

    /**
     * Sets the date of birth of the member.
     * @param dateOfBirth The new date of birth of the member.
     */
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    /**
     * Gets the gender of the member.
     * @return The gender of the member.
     */
    public Gender getGender() { return gender; }

    /**
     * Sets the gender of the member.
     * @param gender The new gender of the member.
     */
    public void setGender(Gender gender) { this.gender = gender; }

    /**
     * Gets the phone number of the member.
     * @return The phone number of the member.
     */
    public String getPhone() { return phone; }

    /**
     * Sets the phone number of the member.
     * @param phone The new phone number of the member.
     */
    public void setPhone(String phone) { this.phone = phone; }

    /**
     * Gets the role of the member.
     * @return The role of the member.
     */
    public Role getRole() { return role; }

    /**
     * Sets the role of the member.
     * @param role The new role of the member.
     */
    public void setRole(Role role) { this.role = role; }

    /**
     * Gets the creation time of the member.
     * @return The creation time of the member.
     */
    public LocalDateTime getCreatedAt() { return createdAt; }

    /**
     * Sets the creation time of the member.
     * @param createdAt The new creation time of the member.
     */
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    /**
     * Gets the active status of the member.
     * @return The active status of the member.
     */
    public boolean isActive() { return active; }

    /**
     * Sets the active status of the member.
     * @param active The new active status of the member.
     */
    public void setActive(boolean active) { this.active = active; }
}