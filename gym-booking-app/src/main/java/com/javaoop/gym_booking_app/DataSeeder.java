package com.javaoop.gym_booking_app; // Or any package you prefer

import com.javaoop.gym_booking_app.repository.AnnouncementRepository;
import com.javaoop.gym_booking_app.service.AnnouncementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1) // Use @Order to control the execution order if you have multiple runners
public class DataSeeder implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataSeeder.class);

    private final AnnouncementService announcementService;
    private final AnnouncementRepository announcementRepository;

    // We use constructor injection to get the necessary service and repository
    public DataSeeder(AnnouncementService announcementService, AnnouncementRepository announcementRepository) {
        this.announcementService = announcementService;
        this.announcementRepository = announcementRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // This method will be executed on application startup
        seedAnnouncements();
    }

    private void seedAnnouncements() {
        // CRITICAL CHECK: Only seed data if the table is empty to avoid duplicates
        if (announcementRepository.count() == 0) {

            announcementService.addAnnouncement(
                    "🎉 新會員首月半價",
                    "凡 6 月新加入會員者，首月費用享 5 折優惠，機會難得，歡迎洽詢櫃台！"
            );

            announcementService.addAnnouncement(
                    "📢 端午連假營運時間公告",
                    "6/8～6/10（六～一）營業時間調整為 10:00－18:00，敬請配合。"
            );

            announcementService.addAnnouncement(
                    "🔥 夏季團課開跑！",
                    "6 月起開放報名「燃脂有氧」、「肌力雕塑」、「TRX 訓練」等團體課程，限量名額，先搶先贏！"
            );

            announcementService.addAnnouncement(
                    "🎉 暑期早鳥優惠開跑囉！",
                    "即日起至 6 月 30 日止，凡預約 7 月至 8 月場地享 8 折優惠！歡迎提早預約，享有最佳時段。"
            );

            announcementService.addAnnouncement(
                    "🌟 營業時間調整通知",
                    "因應政府防疫措施，健身房營業時間調整為早上6:00至晚上10:00，請會員配合"
            );
        }
    }
}