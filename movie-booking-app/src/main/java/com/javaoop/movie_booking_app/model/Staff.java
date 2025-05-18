package com.javaoop.movie_booking_app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

/**
 * Stores information for operational staff managing the cinema system.
 */
@Entity
@Table(name = "Staff", indexes = {
        @Index(name = "idx_username", columnList = "username")
})
public class Staff {
    /**
     * Uid for staff member.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id", nullable = false, updatable = false)
    private Long staffId;

    /**
     * Username for staff login.
     */
    @Column(name = "username", nullable = false, unique = true, length = 100)
    private String username;

    /**
     * Hashed password for staff.
     */
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    /*
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
    */

    public Staff() {
    }

    public Staff(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
        // this.isActive = true; // Would be set by default if uncommented and used
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /*
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
    */

    // toString() method
    @Override
    public String toString() {
        return "Staff{" +
                "staffId=" + staffId +
                ", username='" + username + '\'' +
                ", passwordHash=" + passwordHash +
                // (isActive ? ", isActive=" + isActive : "") + // if uncommented, consider if needed in toString
                '}';
    }
}