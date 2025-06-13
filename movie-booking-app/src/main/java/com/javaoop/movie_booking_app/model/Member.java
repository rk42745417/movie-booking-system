package com.javaoop.movie_booking_app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

import java.time.LocalDate; // Using java.time.LocalDate for DATE type

/**
 * Represents a member or user of the movie booking system.
 * <p>
 * Each member has a unique ID, email address, hashed password, and date of birth.
 * Date of birth is used for age verification against movie rating restrictions.
 * </p>
 */
@Entity
@Table(name = "Members", indexes = {
        @Index(name = "idx_email", columnList = "email")
})
public class Member {
    /**
     * Unique identifier for the member.
     * Corresponds to the primary key in the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false, updatable = false)
    private Long memberId; // Using Long for INT UNSIGNED AUTO_INCREMENT for wider compatibility

    /**
     * Member's email address.
     * Must be unique and is required for login.
     */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * Hashed password string.
     * Stored securely and required for authentication.
     */
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    /**
     * Member's date of birth.
     * Used for verifying age restrictions on movies.
     */
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    /**
     * Default no-argument constructor required by JPA.
     */
    public Member() {
    }

    /**
     * Constructs a new Member with the specified email, hashed password, and date of birth.
     *
     * @param email        the member's email address
     * @param passwordHash the hashed password
     * @param dateOfBirth  the member's date of birth
     */
    public Member(String email, String passwordHash, LocalDate dateOfBirth) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Returns the unique identifier of the member.
     *
     * @return the member ID
     */
    public Long getMemberId() {
        return memberId;
    }

    /**
     * Sets the unique identifier of the member.
     * Usually managed by JPA and not set manually.
     *
     * @param memberId the member ID to set
     */
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    /**
     * Returns the member's email address.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the member's email address.
     *
     * @param email the email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the hashed password string.
     *
     * @return the hashed password
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Sets the hashed password string.
     *
     * @param passwordHash the hashed password to set
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Returns the member's date of birth.
     *
     * @return the date of birth
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the member's date of birth.
     *
     * @param dateOfBirth the date of birth to set
     */
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Returns a string representation of the member object.
     *
     * @return string summarizing member details (excluding sensitive info like plain password)
     */
    @Override
    public String toString() {
        return "Member{" +
                "memberId=" + memberId +
                ", email='" + email + '\'' +
                ", passwordHash=" + passwordHash +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
