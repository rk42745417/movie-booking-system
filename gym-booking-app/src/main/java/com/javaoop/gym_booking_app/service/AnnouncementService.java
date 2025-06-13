package com.javaoop.gym_booking_app.service;

import com.javaoop.gym_booking_app.model.Announcement;
import com.javaoop.gym_booking_app.repository.AnnouncementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for handling announcements.
 */
@Service
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    /**
     * Constructs an AnnouncementService with the given AnnouncementRepository.
     * @param announcementRepository The repository for handling announcement data.
     */
    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    /**
     * Gets all announcements, ordered by creation date in descending order.
     * @return A list of all announcements, ordered by creation date in descending order.
     */
    public List<Announcement> getAllAnnouncements() {
        return announcementRepository.findByOrderByCreatedAtDesc();
    }

    /**
     * Adds a new announcement.
     * @param title The title of the new announcement.
     * @param content The content of the new announcement.
     * @return The new announcement.
     */
    public Announcement addAnnouncement(String title, String content) {
        Announcement announcement = new Announcement(title, content);
        return announcementRepository.save(announcement);
    }

    /**
     * Deletes an announcement.
     * @param id The ID of the announcement to delete.
     */
    public void deleteAnnouncement(Long id) {
        announcementRepository.deleteById(id);
    }
}