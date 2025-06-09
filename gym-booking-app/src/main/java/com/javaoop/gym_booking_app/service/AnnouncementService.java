package com.javaoop.gym_booking_app.service;

import com.javaoop.gym_booking_app.model.Announcement;
import com.javaoop.gym_booking_app.repository.AnnouncementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    // 取得全部公告（依建立時間降序）
    public List<Announcement> getAllAnnouncements() {
        return announcementRepository.findByOrderByCreatedAtDesc();
    }

    // 新增公告
    public Announcement addAnnouncement(String title, String content) {
        Announcement announcement = new Announcement(title, content);
        return announcementRepository.save(announcement);
    }

    // 刪除公告
    public void deleteAnnouncement(Long id) {
        announcementRepository.deleteById(id);
    }
}
