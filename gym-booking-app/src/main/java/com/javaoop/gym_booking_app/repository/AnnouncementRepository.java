package com.javaoop.gym_booking_app.repository;

import com.javaoop.gym_booking_app.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for handling announcements.
 */
@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    /**
     * Finds all announcements and orders them by creation date in descending order.
     * @return A list of all announcements ordered by creation date in descending order.
     */
    List<Announcement> findByOrderByCreatedAtDesc();
}