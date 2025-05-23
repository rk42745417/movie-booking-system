package com.javaoop.movie_booking_app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

import java.time.LocalDate; // Using java.time.LocalDate for DATE type

@Entity
@Table(name = "Members", indexes = {
        @Index(name = "idx_email", columnList = "email")
})
public class Member {
    /**
     * Uid for the member.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false, updatable = false)
    private Long memberId; // Using Long for INT UNSIGNED AUTO_INCREMENT for wider compatibility

    /**
     * Email address.
     */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * Hashed password.
     */
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    /**
     * Date of birth, used for age verification against movie ratings.
     */
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    public Member() {
    }

    public Member(String email, String passwordHash, LocalDate dateOfBirth) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.dateOfBirth = dateOfBirth;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

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