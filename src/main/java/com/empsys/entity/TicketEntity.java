package com.empsys.entity;

import javax.persistence.*;

@Entity
@Table(name = "tickets")
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String empId;
    private String empName;
    private String email;
    private String subject;

    @Column(length = 1000)
    private String description;

    private String status = "Pending"; // ✅ Default status
    
    @Column(length = 1000)
    private String hrReply; // ✅ Add this field

    // Getter & Setter
   


    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmpId() { return empId; }
    public void setEmpId(String empId) { this.empId = empId; }

    public String getEmpName() { return empName; }
    public void setEmpName(String empName) { this.empName = empName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getHrReply() {
        return hrReply;
    }

    public void setHrReply(String hrReply) {
        this.hrReply = hrReply;
    }
	
}
