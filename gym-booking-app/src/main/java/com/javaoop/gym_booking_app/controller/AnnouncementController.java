package com.javaoop.gym_booking_app.controller;

import com.javaoop.gym_booking_app.model.Announcement;
import com.javaoop.gym_booking_app.service.AnnouncementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/announcements")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService service) {
        this.announcementService = service;
    }

    // 取得全部公告
    @GetMapping
    public List<Announcement> all() {
        return announcementService.getAllAnnouncements();
    }

    // 新增公告
    @PostMapping
    public Announcement add(@RequestBody Announcement req) {
        return announcementService.addAnnouncement(req.getTitle(), req.getContent());
    }

    // 刪除公告
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        announcementService.deleteAnnouncement(id);
    }
}
