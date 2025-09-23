

package com.empsys.service;

import com.empsys.dao.AttendanceRepository;
import com.empsys.entity.AttendanceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    // ✅ Save Clock-In
    public AttendanceEntity saveClockIn(AttendanceEntity attendance) {
        LocalDate today = LocalDate.now();
        Optional<AttendanceEntity> existing = attendanceRepository.findByEmailAndDate(attendance.getEmail(), today);

        if (existing.isPresent()) {
            throw new RuntimeException("Already clocked in today.");
        }

        attendance.setDate(today);
        attendance.setStatus("Present");
        return attendanceRepository.save(attendance);
    }

    // ✅ Save Clock-Out and Calculate Total Hours
    public AttendanceEntity saveClockOut(String email, String outTime, String ignoredParam) {
        LocalDate today = LocalDate.now();
        Optional<AttendanceEntity> optional = attendanceRepository.findByEmailAndDate(email, today);

        if (optional.isPresent()) {
            AttendanceEntity record = optional.get();

            if (record.getOutTime() != null) {
                throw new RuntimeException("Already clocked out today.");
            }

            record.setOutTime(outTime);
            calculateTotalHours(record);
            return attendanceRepository.save(record);
        } else {
            throw new RuntimeException("No Clock-In record found for today.");
        }
    }

    // ✅ Calculate Total Hours from In & Out time
    private void calculateTotalHours(AttendanceEntity record) {
        String inTime = record.getInTime();
        String outTime = record.getOutTime();

        if (inTime != null && outTime != null && inTime.contains(":") && outTime.contains(":")) {
            try {
                String[] inParts = inTime.split(":");
                String[] outParts = outTime.split(":");

                int inHour = Integer.parseInt(inParts[0]);
                int inMin = Integer.parseInt(inParts[1]);
                int outHour = Integer.parseInt(outParts[0]);
                int outMin = Integer.parseInt(outParts[1]);

                int inTotal = inHour * 60 + inMin;
                int outTotal = outHour * 60 + outMin;

                int durationMin = outTotal - inTotal;
                int hours = durationMin / 60;
                int minutes = durationMin % 60;

                record.setTotalHours(hours + "h " + minutes + "m");
            } catch (Exception e) {
                record.setTotalHours("Invalid time format");
            }
        } else {
            record.setTotalHours("Time not available");
        }
    }

    // ✅ HR updates out-time manually
    public AttendanceEntity updateOutTime(String email, LocalDate date, String newOutTime) {
        Optional<AttendanceEntity> optional = attendanceRepository.findByEmailAndDate(email, date);

        if (optional.isPresent()) {
            AttendanceEntity record = optional.get();
            record.setOutTime(newOutTime);

            // Recalculate total hours if inTime is available
            if (record.getInTime() != null && newOutTime != null) {
                calculateTotalHours(record);
            } else {
                record.setTotalHours("Time not available");
            }

            return attendanceRepository.save(record);
        } else {
            throw new RuntimeException("Attendance record not found for update.");
        }
    }

    // ✅ Check if already clocked in today
    public boolean hasClockedIn(String email) {
        return attendanceRepository.existsByEmailAndDate(email, LocalDate.now());
    }

    // ✅ Check if attendance is completed (in + out)
    public boolean isAttendanceCompleted(String email) {
        Optional<AttendanceEntity> optional = attendanceRepository.findByEmailAndDate(email, LocalDate.now());
        return optional.isPresent() && optional.get().getOutTime() != null;
    }

    // ✅ Get today's attendance for employee
    public Optional<AttendanceEntity> getTodayAttendance(String email) {
        return attendanceRepository.findByEmailAndDate(email, LocalDate.now());
    }

    // ✅ Get all attendance for HR view
    public List<AttendanceEntity> getAllAttendance() {
        return attendanceRepository.findAllByOrderByDateDesc();
    }

    // ✅ Get all attendance by email
    public List<AttendanceEntity> getAttendanceByEmail(String email) {
        return attendanceRepository.findByEmail(email);
    }

    // ✅ Optional: Mark Absent for missing records (used by hr-view merge logic)
    public AttendanceEntity markAbsentIfNoRecord(String email) {
        LocalDate today = LocalDate.now();
        Optional<AttendanceEntity> optional = attendanceRepository.findByEmailAndDate(email, today);

        if (optional.isEmpty()) {
            AttendanceEntity absent = new AttendanceEntity();
            absent.setEmail(email);
            absent.setDate(today);
            absent.setStatus("Absent");
            absent.setInTime(null);
            absent.setOutTime(null);
            absent.setTotalHours("0h 0m");
            return attendanceRepository.save(absent);
        }

        return optional.get();
    }
}

