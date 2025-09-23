package com.empsys.dao;

import com.empsys.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    // ✅ Fetch announcements created between two dates
    List<Announcement> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    // ✅ Delete announcements created before a specific date
    void deleteByCreatedAtBefore(LocalDateTime cutoffDate);
}
