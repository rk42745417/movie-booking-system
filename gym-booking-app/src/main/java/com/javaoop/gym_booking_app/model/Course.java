package com.javaoop.gym_booking_app.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

/** Represents a single course or class session. */
@Entity
@Table(name = "course")
public class Course {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 40)
    private String title;

    @Column(name = "description", length = 1000)
    private String description;

    /* coach_id and gym_room_id can be null to allow for default values */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coach_id")
    private Coach coach;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_room_id")
    private GymRoom room;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CourseStatus status;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "course_tags", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "tag", nullable = false)
    private Set<String> tags = new HashSet<>();



    @Transient // Not in the database, only for calculation
    private int reservedCount;

    /* ---------- getters and setters ---------- */

    /**
     * Gets the ID of the course.
     * @return The ID of the course.
     */
    public Long getId() { return id; }

    /**
     * Sets the ID of the course.
     * @param id The new ID of the course.
     */
    public void setId(Long id) { this.id = id; }

    /**
     * Gets the title of the course.
     * @return The title of the course.
     */
    public String getTitle() { return title; }

    /**
     * Sets the title of the course.
     * @param title The new title of the course.
     */
    public void setTitle(String title) { this.title = title; }

    /**
     * Gets the description of the course.
     * @return The description of the course.
     */
    public String getDescription() { return description; }

    /**
     * Sets the description of the course.
     * @param description The new description of the course.
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * Gets the coach of the course.
     * @return The coach of the course.
     */
    public Coach getCoach() { return coach; }

    /**
     * Sets the coach of the course.
     * @param coach The new coach of the course.
     */
    public void setCoach(Coach coach) { this.coach = coach; }

    /**
     * Gets the room of the course.
     * @return The room of the course.
     */
    public GymRoom getRoom() { return room; }

    /**
     * Sets the room of the course.
     * @param room The new room of the course.
     */
    public void setRoom(GymRoom room) { this.room = room; }

    /**
     * Gets the capacity of the course.
     * @return The capacity of the course.
     */
    public Integer getCapacity() { return capacity; }

    /**
     * Sets the capacity of the course.
     * @param capacity The new capacity of the course.
     */
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    /**
     * Gets the start time of the course.
     * @return The start time of the course.
     */
    public LocalDateTime getStartTime() { return startTime; }

    /**
     * Sets the start time of the course.
     * @param startTime The new start time of the course.
     */
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    /**
     * Gets the end time of the course.
     * @return The end time of the course.
     */
    public LocalDateTime getEndTime() { return endTime; }

    /**
     * Sets the end time of the course.
     * @param endTime The new end time of the course.
     */
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    /**
     * Gets the status of the course.
     * @return The status of the course.
     */
    public CourseStatus getStatus() { return status; }

    /**
     * Sets the status of the course.
     * @param status The new status of the course.
     */
    public void setStatus(CourseStatus status) { this.status = status; }

    /**
     * Gets the tags of the course.
     * @return The tags of the course.
     */
    public Set<String> getTags() { return tags; }

    /**
     * Sets the tags of the course.
     * @param tags The new tags of the course.
     */
    public void setTags(Set<String> tags) { this.tags = tags; }


    /**
     * Gets the number of reserved spots for the course.
     * @return The number of reserved spots for the course.
     */
    public int getReservedCount() {
        return reservedCount;
    }

    /**
     * Sets the number of reserved spots for the course.
     * @param reservedCount The new number of reserved spots for the course.
     */
    public void setReservedCount(int reservedCount) {
        this.reservedCount = reservedCount;
    }
}