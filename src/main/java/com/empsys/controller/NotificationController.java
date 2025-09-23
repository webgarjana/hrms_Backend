package com.empsys.controller;

import com.empsys.dao.NotificationRepository;
import com.empsys.entity.NotificationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")

@CrossOrigin(origins = "http://localhost:3000")
public class NotificationController {

    @Autowired
    private NotificationRepository repo;

    
    
    @GetMapping("/unseen/{email}")
    public ResponseEntity<?> getUnseen(@PathVariable String email) {
        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid email");
        }
        List<NotificationEntity> list = repo.findByReceiverEmailAndSeenFalse(email);
        return ResponseEntity.ok(list);
    }


    // ✅ 2. Mark a notification as seen
    @PostMapping("/mark-seen/{id}")
    public ResponseEntity<?> markSeen(@PathVariable Long id) {
        NotificationEntity n = repo.findById(id).orElse(null);
        if (n != null) {
            n.setSeen(true);
            repo.save(n);
            return ResponseEntity.ok("Seen");
        }
        return ResponseEntity.badRequest().body("Notification not found");
    }

    // ✅ 3. Add a new notification
    @PostMapping("/add")
    public ResponseEntity<?> addNotification(@RequestBody NotificationEntity notification) {
        notification.setTimestamp(LocalDateTime.now());
        notification.setSeen(false);
        repo.save(notification);
        return ResponseEntity.ok("Notification Added");
    }

    // ✅ 4. Get all notifications for a specific receiver
    @GetMapping("/{email}")
    public List<NotificationEntity> getUserNotifications(@PathVariable String email) {
        return repo.findByReceiverEmail(email);
    }
}
