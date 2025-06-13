package com.javaoop.gym_booking_app.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

/**
 * Represents a coach entity using Jakarta Persistence (JPA).
 */
@Entity
@Table(name = "coach")
public class Coach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "bio", length = 500)
    private String bio;

    @Column(name = "certifications")
    private String certifications;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "avatar_url")
    private String avatarUrl;


    /**
     * Default constructor.
     */
    public Coach() {
    }

    /**
     * Gets the ID of the coach.
     * @return The ID of the coach.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the coach.
     * @param id The new ID of the coach.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the coach.
     * @return The name of the coach.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the coach.
     * @param name The new name of the coach.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the bio of the coach.
     * @return The bio of the coach.
     */
    public String getBio() {
        return bio;
    }

    /**
     * Sets the bio of the coach.
     * @param bio The new bio of the coach.
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * Gets the certifications of the coach.
     * @return The certifications of the coach.
     */
    public String getCertifications() {
        return certifications;
    }

    /**
     * Sets the certifications of the coach.
     * @param certifications The new certifications of the coach.
     */
    public void setCertifications(String certifications) {
        this.certifications = certifications;
    }

    /**
     * Gets the phone number of the coach.
     * @return The phone number of the coach.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the coach.
     * @param phone The new phone number of the coach.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the email of the coach.
     * @return The email of the coach.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the coach.
     * @param email The new email of the coach.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the avatar URL of the coach.
     * @return The avatar URL of the coach.
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * Sets the avatar URL of the coach.
     * @param avatarUrl The new avatar URL of the coach.
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}