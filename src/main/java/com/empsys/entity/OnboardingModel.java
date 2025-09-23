
package com.empsys.entity;

import javax.persistence.*;

@Entity
@Table(name = "onboarding")
public class OnboardingModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id", unique = true)
    private String employeeId;

    @Column(unique = true)
    private String email;

    private String fullName;
    private String designation;
    private boolean active = true;

    // Getters and Setters
    public Long getId() { return id; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}