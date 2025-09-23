package com.empsys.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empsys.dao.LeaveRepository;
import com.empsys.dao.NotificationRepository;
import com.empsys.entity.LeaveEntity;
import com.empsys.entity.NotificationEntity;

@Service
public class LeaveService {

    @Autowired
    private LeaveRepository repo;

    @Autowired
    private NotificationRepository notificationRepo;

 // ✅ When employee applies leave, HR is notified
    public LeaveEntity applyLeave(LeaveEntity leave) {
        leave.setStatus("Pending");
        leave.setAppliedDate(LocalDate.now());
        LeaveEntity savedLeave = repo.save(leave);

        NotificationEntity notification = new NotificationEntity();
        notification.setSenderEmail(leave.getEmail()); // employee email
        notification.setReceiverEmail("hr@gmail.com"); // HR sees this
        notification.setMessage("Leave request from " + leave.getEmployeeId() + " (" + leave.getEmail() + ")");
        notification.setSeen(false);
        notification.setTimestamp(LocalDateTime.now());

        notificationRepo.save(notification);
        return savedLeave;
    }

    public List<LeaveEntity> getAllLeaves() {
        return repo.findAll();
    }

    public List<LeaveEntity> getLeavesByEmail(String email) {
        return repo.findByEmail(email);
    }

    // ✅ When HR updates leave status, notify the employee
    public LeaveEntity updateStatus(Long id, String status, String hrMessage) {
        LeaveEntity leave = repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Leave not found"));
        
        leave.setStatus(status);
        leave.setHrMessage(hrMessage);
        LeaveEntity updatedLeave = repo.save(leave);

        // ✅ Notify the specific employee
        NotificationEntity employeeNotification = new NotificationEntity();
        employeeNotification.setSenderEmail("hr@gmail.com");
        employeeNotification.setReceiverEmail(leave.getEmail()); // employee sees this
        employeeNotification.setMessage("Your leave request has been " + status + ". Message: " + hrMessage);
        employeeNotification.setSeen(false);
        employeeNotification.setTimestamp(LocalDateTime.now());

        notificationRepo.save(employeeNotification);
        return updatedLeave;
    }



//    public List<LeaveEntity> getAllLeaves() {
//        return repo.findAll();
//    }
//
//    public List<LeaveEntity> getLeavesByEmail(String email) {
//        return repo.findByEmail(email);
//    }
//
//    public LeaveEntity updateStatus(Long id, String status, String hrMessage) {
//        LeaveEntity leave = repo.findById(id)
//            .orElseThrow(() -> new RuntimeException("Leave not found"));
//        leave.setStatus(status);
//        leave.setHrMessage(hrMessage);
//        return repo.save(leave);
//    }
    
    
    
    public LeaveEntity getLeaveById(Long id) {
        return repo.findById(id).orElse(null);
    }

}
