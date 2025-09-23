package com.empsys.entity;


	
	import javax.persistence.*;
	import java.time.LocalDate;

	@Entity
	@Table(name = "attendance")
	public class AttendanceEntity {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String employeeId; // ✅ Added field

	    private String email;
	    private LocalDate date;
	    private String inTime;
	    private String outTime;
	    private String totalHours;
	    private String status;

	    public AttendanceEntity() {}

	    public AttendanceEntity(String employeeId, String email, LocalDate date, String inTime, String outTime, String totalHours, String status) {
	        this.employeeId = employeeId;
	        this.email = email;
	        this.date = date;
	        this.inTime = inTime;
	        this.outTime = outTime;
	        this.totalHours = totalHours;
	        this.status = status;
	    }

	    // Getters and Setters

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getEmployeeId() {
	        return employeeId;
	    }

	    public void setEmployeeId(String employeeId) {
	        this.employeeId = employeeId;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public LocalDate getDate() {
	        return date;
	    }

	    public void setDate(LocalDate date) {
	        this.date = date;
	    }

	    public String getInTime() {
	        return inTime;
	    }

	    public void setInTime(String inTime) {
	        this.inTime = inTime;
	    }

	    public String getOutTime() {
	        return outTime;
	    }

	    public void setOutTime(String outTime) {
	        this.outTime = outTime;
	    }

	    public String getTotalHours() {
	        return totalHours;
	    }

	    public void setTotalHours(String totalHours) {
	        this.totalHours = totalHours;
	    }

	    public String getStatus() {
	        return status;
	    }

	    public void setStatus(String status) {
	        this.status = status;
	    }
	}
