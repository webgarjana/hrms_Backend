package com.empsys.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String employeeEmail;
    private String title;
    private String description;
    private String tools;
    private LocalDate deadline;

    private boolean completed = false;
    
    @Column(length = 1000)
    private String hrReply;

    // --- Constructors ---

    public TaskEntity() {
    }

    public TaskEntity(String hrReply,String employeeEmail, String title, String description, String tools, LocalDate deadline, boolean completed) {
        this.employeeEmail = employeeEmail;
        this.title = title;
        this.description = description;
        this.tools = tools;
        this.deadline = deadline;
        this.completed = completed;
    }

    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTools() {
        return tools;
    }

    public void setTools(String tools) {
        this.tools = tools;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    public String getHrReply() {
		return hrReply;
	}
	public void setHrReply(String hrReply) {
		this.hrReply = hrReply;
	}
    

   

    
}
