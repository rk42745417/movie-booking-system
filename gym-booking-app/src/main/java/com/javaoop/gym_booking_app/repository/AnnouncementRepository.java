package com.javaoop.gym_booking_app.repository;

import com.javaoop.gym_booking_app.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findByOrderByCreatedAtDesc();
    // 只要繼承 JpaRepository 就有基本的 CRUD（新增、查詢、刪除、全部查詢...）
}
