package com.empsys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.empsys.entity.NotificationEntity;
import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

    // Fetch all notifications where the receiver is the given email
    List<NotificationEntity> findByReceiverEmail(String email);

    // Fetch unseen notifications for a receiver
    List<NotificationEntity> findByReceiverEmailAndSeenFalse(String email);
}
