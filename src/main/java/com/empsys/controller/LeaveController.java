package com.empsys.controller;

import com.empsys.entity.LeaveEntity;
import com.empsys.service.LeaveService;
import com.itextpdf.io.exceptions.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/leave")
@CrossOrigin(origins = "http://localhost:3000")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    // ✅ Apply leave
    @PostMapping("/apply")
    public LeaveEntity applyLeave(@RequestBody LeaveEntity leave) {
        return leaveService.applyLeave(leave); // Use service correctly
    }

    // ✅ Get all leave requests (HR)
    @GetMapping("/all")
    public List<LeaveEntity> getAllLeaves() {
        return leaveService.getAllLeaves();
    }

    // ✅ Get user leave requests by email
    @GetMapping("/user")
    public List<LeaveEntity> getLeavesByEmail(@RequestParam String email) {
        return leaveService.getLeavesByEmail(email);
    }

    // ✅ HR approves or rejects
    @PutMapping("/status/{id}")
    public LeaveEntity updateLeaveStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body
    ) {
        String status = body.get("status");
        String msg = body.getOrDefault("hrMessage", "");
        return leaveService.updateStatus(id, status, msg);
    }
    
    
    //new
    @PostMapping(value = "/apply", consumes = {"multipart/form-data"})
    public LeaveEntity applyLeave(
            @RequestParam String employeeId,
            @RequestParam String email,
            @RequestParam String leaveType,
            @RequestParam String fromDate,
            @RequestParam String toDate,
            @RequestParam String reason,
            @RequestPart(required = false) MultipartFile attachment
    ) throws IOException, java.io.IOException {
        LeaveEntity leave = new LeaveEntity();
        leave.setEmployeeId(employeeId);
        leave.setEmail(email);
        leave.setLeaveType(leaveType);
        leave.setFromDate(LocalDate.parse(fromDate));
        leave.setToDate(LocalDate.parse(toDate));
        leave.setReason(reason);

        if (attachment != null && !attachment.isEmpty()) {
            leave.setAttachmentName(attachment.getOriginalFilename());
            leave.setAttachmentData(attachment.getBytes());  // store file as bytes
        }

        return leaveService.applyLeave(leave);
    }
    
    @GetMapping("/attachment/{id}")
    public ResponseEntity<byte[]> getAttachment(@PathVariable Long id) {
        LeaveEntity leave = leaveService.getLeaveById(id);
        if (leave == null || leave.getAttachmentData() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + leave.getAttachmentName() + "\"")
            .body(leave.getAttachmentData());
    }

    
}
