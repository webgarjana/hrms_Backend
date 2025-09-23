

package com.empsys.service;

import com.empsys.dao.AnnouncementRepository;
//import com.empsys.dao.EmployeeRepository;
import com.empsys.dao.NotificationRepository;
import com.empsys.dao.OnboardingRepository;
import com.empsys.entity.Announcement;
//import com.empsys.entity.Employee;
import com.empsys.entity.NotificationEntity;
import com.empsys.entity.OnboardingEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository repository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private OnboardingRepository onboardingRepository; // ⬅️ to fetch all employees

    public Announcement saveAnnouncement(Announcement announcement) {
        Announcement saved = repository.save(announcement);

        // ✅ Send notification to all employees
//        List<OnboardingEntity> allEmployees = onboardingRepository.findAll();
//        for (OnboardingEntity emp : allEmployees) {
//            NotificationEntity notification = new NotificationEntity();
//            notification.setSenderEmail("hr@gmail.com");
//            notification.setReceiverEmail(emp.getEmail());
//            notification.setMessage("New announcement: " + saved.getTitle());
//            notification.setSeen(false);
//            notification.setTimestamp(LocalDateTime.now());
//            notificationRepository.save(notification);
//        }
//
//        return saved;
//    }
        
     // ✅ Send notification to all employees except HR
        List<OnboardingEntity> allEmployees = onboardingRepository.findAll();
        for (OnboardingEntity emp : allEmployees) {
            if (!"hr".equalsIgnoreCase(emp.getRole())) {
                NotificationEntity notification = new NotificationEntity();
                notification.setSenderEmail("hr@gmail.com");
                notification.setReceiverEmail(emp.getEmail());
                notification.setMessage("New announcement: " + saved.getTitle());
                notification.setSeen(false);
                notification.setTimestamp(LocalDateTime.now());
                notificationRepository.save(notification);
            }
        }

        return saved;
    }

    public List<Announcement> getAllAnnouncements() {
        return repository.findAll();
    }

    public Announcement getAnnouncementById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Announcement> getAnnouncementsBetween(LocalDateTime start, LocalDateTime end) {
        return repository.findByCreatedAtBetween(start, end);
    }
}

