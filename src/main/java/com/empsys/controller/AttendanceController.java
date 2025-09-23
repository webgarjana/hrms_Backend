

package com.empsys.controller;

import com.empsys.dao.AttendanceRepository;
import com.empsys.dao.OnboardingRepository;
import com.empsys.entity.AttendanceEntity;
import com.empsys.entity.OnboardingEntity;
import com.empsys.service.AttendanceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "http://localhost:3000")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private OnboardingRepository onboardingRepository; // ✅ Required for merging

    // ✅ Clock In
    @PostMapping("/clock-in")
    public ResponseEntity<?> clockIn(@RequestBody AttendanceEntity attendance) {
        try {
            AttendanceEntity saved = attendanceService.saveClockIn(attendance);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            // instead of 500, return a friendly message
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    // ✅ Clock Out
    @PostMapping("/clock-out")
    public AttendanceEntity clockOut(@RequestBody Map<String, String> data) {
        String email = data.get("email");
        String outTime = data.get("outTime");
        String totalHours = data.get("totalHours");
        return attendanceService.saveClockOut(email, outTime, totalHours);
    }

    // ✅ Check if employee has clocked in
    @GetMapping("/check-clock-in")
    public boolean checkClockIn(@RequestParam String email) {
        return attendanceService.hasClockedIn(email);
    }

    // ✅ Get today's attendance for employee
    @GetMapping("/today")
    public Optional<AttendanceEntity> getToday(@RequestParam String email) {
        return attendanceService.getTodayAttendance(email);
    }

    // ✅ HR: get all attendance records (original)
    @GetMapping("/all")
    public List<AttendanceEntity> all() {
        return attendanceService.getAllAttendance();
    }

    // ✅ EMPLOYEE: get attendance history by email
    @GetMapping("/email/{email}")
    public List<AttendanceEntity> getAttendanceByEmail(@PathVariable String email) {
        return attendanceService.getAttendanceByEmail(email);
    }

    // ✅ New: Check today's clock-in/out status
    @GetMapping("/check-today-status")
    public ResponseEntity<Map<String, Boolean>> checkTodayStatus(@RequestParam String email) {
        LocalDate today = LocalDate.now();
        Optional<AttendanceEntity> attendance = attendanceRepository.findByEmailAndDate(email, today);

        Map<String, Boolean> status = new HashMap<>();
        status.put("clockedIn", false);
        status.put("clockedOut", false);

        if (attendance.isPresent()) {
            AttendanceEntity att = attendance.get();
            status.put("clockedIn", att.getInTime() != null);
            status.put("clockedOut", att.getOutTime() != null);
        }

        return ResponseEntity.ok(status);
    }

    // ✅ Update out /in time (HR Edit Save)

    
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateAttendance(@PathVariable Long id, @RequestBody AttendanceEntity updated) {
        Optional<AttendanceEntity> optional = attendanceRepository.findById(id);

        if (optional.isPresent()) {
            AttendanceEntity record = optional.get();

            record.setInTime(updated.getInTime());
            record.setOutTime(updated.getOutTime());

            // ✅ Recalculate totalHours
            if (record.getInTime() != null && record.getOutTime() != null) {
                String[] inParts = record.getInTime().split(":");
                String[] outParts = record.getOutTime().split(":");

                int inMinutes = Integer.parseInt(inParts[0]) * 60 + Integer.parseInt(inParts[1]);
                int outMinutes = Integer.parseInt(outParts[0]) * 60 + Integer.parseInt(outParts[1]);

                int duration = outMinutes - inMinutes;
                if (duration >= 0) {
                    int hours = duration / 60;
                    int mins = duration % 60;
                    record.setTotalHours(hours + "h " + mins + "m");
                } else {
                    record.setTotalHours("Invalid Time");
                }
            } else {
                record.setTotalHours("Time not available");
            }

            attendanceRepository.save(record);
            return ResponseEntity.ok("Attendance updated successfully.");
        } else {
            return ResponseEntity.badRequest().body("Attendance record not found.");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<AttendanceEntity> createAttendance(@RequestBody AttendanceEntity record) {
        if (record.getInTime() != null && record.getOutTime() != null) {
            String[] inParts = record.getInTime().split(":");
            String[] outParts = record.getOutTime().split(":");

            int inMinutes = Integer.parseInt(inParts[0]) * 60 + Integer.parseInt(inParts[1]);
            int outMinutes = Integer.parseInt(outParts[0]) * 60 + Integer.parseInt(outParts[1]);

            int duration = outMinutes - inMinutes;
            if (duration >= 0) {
                int hours = duration / 60;
                int mins = duration % 60;
                record.setTotalHours(hours + "h " + mins + "m");
            } else {
                record.setTotalHours("Invalid Time");
            }

            record.setStatus("Present");
        } else {
            record.setTotalHours("0h 0m");
            record.setStatus("Absent");
        }

        AttendanceEntity saved = attendanceRepository.save(record);
        return ResponseEntity.status(201).body(saved); // Return the saved entity with ID
    }



    
    @GetMapping("/hr-view")
    public List<AttendanceEntity> getHrAttendanceForToday() {
        LocalDate today = LocalDate.now();

        List<OnboardingEntity> allEmployees = onboardingRepository.findAll();
        List<AttendanceEntity> todayRecords = attendanceRepository.findAllByDate(today);

        Map<String, AttendanceEntity> recordMap = new HashMap<>();
        for (AttendanceEntity record : todayRecords) {
            recordMap.put(record.getEmail(), record);
        }

        List<AttendanceEntity> result = new ArrayList<>();
        for (OnboardingEntity emp : allEmployees) {
            if (!"employee".equalsIgnoreCase(emp.getRole())) {
                continue; // ⛔ Skip HRs
            }

            AttendanceEntity att = recordMap.get(emp.getEmail());
            if (att != null) {
                result.add(att);
            } else {
                AttendanceEntity absent = new AttendanceEntity();
                absent.setEmployeeId(emp.getEmployeeId());
                absent.setEmail(emp.getEmail());
                absent.setDate(today);
                absent.setStatus("Absent");
                absent.setInTime(null);
                absent.setOutTime(null);
                absent.setTotalHours("0h 0m");
                result.add(absent);
            }
        }

        return result;
    }

}

