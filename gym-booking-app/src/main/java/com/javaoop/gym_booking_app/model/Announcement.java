package com.javaoop.gym_booking_app.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Represents an announcement.
 */
@Entity
@Table(name = "announcements")
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 40)
    private String title;

    @Column(name = "content", length = 1000)
    private String content; // Announcement content

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // Creation time

    /**
     * Default constructor.
     */
    public Announcement() {}

    /**
     * Constructs an Announcement with the given title and content.
     * @param title The title of the announcement.
     * @param content The content of the announcement.
     */
    public Announcement(String title, String content) {
        this.title = title;
        this.content = content;
    }

    /**
     * Gets the ID of the announcement.
     * @return The ID of the announcement.
     */
    public Long getId() { return id; }

    /**
     * Gets the content of the announcement.
     * @return The content of the announcement.
     */
    public String getContent() { return content; }

    /**
     * Sets the content of the announcement.
     * @param content The new content of the announcement.
     */
    public void setContent(String content) { this.content = content; }

    /**
     * Gets the creation time of the announcement.
     * @return The creation time of the announcement.
     */
    public LocalDateTime getCreatedAt() { return createdAt; }

    /**
     * Sets the creation time of the announcement.
     * @param createdAt The new creation time of the announcement.
     */
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    /**
     * Gets the title of the announcement.
     * @return The title of the announcement.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the announcement.
     * @param title The new title of the announcement.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the ID of the announcement.
     * @param id The new ID of the announcement.
     */
    public void setId(Long id) {
        this.id = id;
    }
}