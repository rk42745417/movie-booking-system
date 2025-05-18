package com.javaoop.movie_booking_app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Stores the main information for each ticket booking.
 * Each booking can relate to multiple Seat objects.
 */
@Entity
@Table(name = "Bookings", indexes = {
        @Index(name = "idx_member_id", columnList = "member_id"),
        @Index(name = "idx_showtime_id", columnList = "showtime_id")
})
public class Booking {
    /**
     * Uid for the booking.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id", nullable = false, updatable = false)
    private Long bookingId;

    /**
     * Foreign key referencing the member who made the booking.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    /**
     * Foreign key referencing the specific showtime booked.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "showtime_id", nullable = false)
    private Showtime showtime;

    /*
    @CreationTimestamp // Automatically sets to current timestamp on creation
    @Column(name = "booking_time", nullable = false, updatable = false)
    private LocalDateTime bookingTime;
    */

    /**
     * Status of the booking (e.g., Confirmed, Cancelled by user, Pending payment).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private BookingStatus status = BookingStatus.PENDING;

    /*
    @Column(name = "total_price", precision = 10, scale = 2)
    private BigDecimal totalPrice;
    */

    public Booking() {
    }

    public Booking(Member member, Showtime showtime) {
        this.member = member;
        this.showtime = showtime;
        // bookingTime would be set automatically if @CreationTimestamp is used
        // status is already defaulted
    }

    // Getters and Setters
    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }

    /*
    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }
    */

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    /*
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    */

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", memberId=" + (member != null ? member.getMemberId() : "null") +
                ", showtimeId=" + (showtime != null ? showtime.getShowtimeId() : "null") +
                // (bookingTime != null ? ", bookingTime=" + bookingTime : "") + // if uncommented
                ", status=" + status +
                // (totalPrice != null ? ", totalPrice=" + totalPrice : "") + // if uncommented
                '}';
    }
}