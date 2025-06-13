package com.javaoop.gym_booking_app.controller;

import com.javaoop.gym_booking_app.model.Announcement;
import com.javaoop.gym_booking_app.service.AnnouncementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling announcements.
 */
@RestController
@RequestMapping("/api/v1/announcements")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    /**
     * Constructs an AnnouncementController with the given AnnouncementService.
     * @param service The service for handling announcement logic.
     */
    public AnnouncementController(AnnouncementService service) {
        this.announcementService = service;
    }

    /**
     * Gets all announcements.
     * @return A list of all announcements.
     */
    @GetMapping
    public List<Announcement> all() {
        return announcementService.getAllAnnouncements();
    }

    /**
     * Adds a new announcement.
     * @param req The request body containing the new announcement.
     * @return The new announcement.
     */
    @PostMapping
    public Announcement add(@RequestBody Announcement req) {
        return announcementService.addAnnouncement(req.getTitle(), req.getContent());
    }

    /**
     * Deletes an announcement.
     * @param id The ID of the announcement to delete.
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        announcementService.deleteAnnouncement(id);
    }
}