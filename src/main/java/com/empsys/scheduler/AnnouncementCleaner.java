package com.empsys.scheduler;

import com.empsys.dao.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AnnouncementCleaner {

    @Autowired
    private AnnouncementRepository repository;

    // ✅ Scheduled to run every day at 1:00 AM
    @Scheduled(cron = "0 0 1 * * ?")
    public void deleteOldAnnouncements() {
        LocalDateTime fifteenDaysAgo = LocalDateTime.now().minusDays(15);
        repository.deleteByCreatedAtBefore(fifteenDaysAgo);
        System.out.println("✅ [Scheduler] Deleted announcements older than 15 days.");
    }
}
