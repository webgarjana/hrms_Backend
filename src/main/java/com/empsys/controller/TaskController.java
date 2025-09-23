package com.empsys.controller;

import com.empsys.entity.TaskEntity;
import com.empsys.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/assign")
    public TaskEntity assignTask(@RequestBody TaskEntity task) {
        return taskService.assignTask(task);
    }

    @GetMapping("/employee")
    public List<TaskEntity> getTasksByEmployee(@RequestParam String email) {
        return taskService.getTasksByEmployee(email);
    }

    @GetMapping("/all")
    public List<TaskEntity> getAllTasks() {
        return taskService.getAllTasks();
    }
}
