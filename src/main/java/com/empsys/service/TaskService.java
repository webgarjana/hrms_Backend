

package com.empsys.service;

import com.empsys.entity.TaskEntity;
import com.empsys.entity.NotificationEntity;
import com.empsys.dao.TaskRepository;
import com.empsys.dao.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepo;

    @Autowired
    private NotificationRepository notificationRepository;

    public TaskEntity assignTask(TaskEntity task) {
        TaskEntity savedTask = taskRepo.save(task);

        // ✅ Send notification to the assigned employee
        NotificationEntity notification = new NotificationEntity();
        notification.setSenderEmail("hr@gmail.com");
        notification.setReceiverEmail(task.getEmployeeEmail());
        notification.setMessage("New task assigned: \"" + task.getTitle() + "\" with deadline " + task.getDeadline());
        notification.setSeen(false);
        notification.setTimestamp(LocalDateTime.now());
        notificationRepository.save(notification);

        return savedTask;
    }

    public List<TaskEntity> getTasksByEmployee(String email) {
        return taskRepo.findByEmployeeEmail(email);
    }

    public List<TaskEntity> getAllTasks() {
        return taskRepo.findAll();
    }
}

