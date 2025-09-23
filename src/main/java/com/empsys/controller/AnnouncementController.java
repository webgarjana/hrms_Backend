 
package com.empsys.controller;

import com.empsys.entity.Announcement;
import com.empsys.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@RestController
@RequestMapping("/api/announcements")
@CrossOrigin(origins = "*")
public class AnnouncementController {

    @Autowired
    private AnnouncementService service;

    // ✅ DTO for frontend JSON input
    public static class SimpleAnnouncement {
        public String title;
        public String content;
    }

    // ✅ Add announcement with JSON only
    @PostMapping("/add")
    public ResponseEntity<Announcement> addAnnouncement(@RequestBody SimpleAnnouncement input) {
        try {
            Announcement announcement = new Announcement();
            announcement.setTitle(input.title);
            announcement.setContent(input.content);
            Announcement saved = service.saveAnnouncement(announcement);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // ✅ Get all announcements (if needed)
    @GetMapping("/all")
    public List<Announcement> getAll() {
        return service.getAllAnnouncements();
    }

    // ✅ Get today's announcements for dashboard
    @GetMapping("/dashboard/announcements/today")
    public List<Map<String, Object>> getTodaysAnnouncements() {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        List<Announcement> announcements = service.getAnnouncementsBetween(start, end);

        List<Map<String, Object>> response = new ArrayList<>();
        for (Announcement a : announcements) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", a.getId());
            map.put("title", a.getTitle());
            map.put("content", a.getContent());
            response.add(map);
        }

        return response;
    }

    // ✅ Dummy celebrations endpoint
//    @GetMapping("/dashboard/celebrations/today")
//    public List<Map<String, String>> getCelebrationsToday() {
//        List<Map<String, String>> celebrations = new ArrayList<>();
//
//        celebrations.add(Map.of(
//            "name", "Pooja Lakhangave",
//            "type", "Birthday",
//            "date", "2025-07-15"
//        ));
//        celebrations.add(Map.of(
//            "name", "Rohan Deshmukh",
//            "type", "Work Anniversary",
//            "date", "2025-07-15"
//        ));
//
//        return celebrations;
//    }
}
