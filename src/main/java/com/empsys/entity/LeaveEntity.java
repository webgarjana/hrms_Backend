package com.empsys.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "leave_requests")
public class LeaveEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String employeeId;

    private String email;
    private String leaveType;

    private LocalDate fromDate;
    private LocalDate toDate;
    
    private LocalDate appliedDate;

    private String reason;

    private String status;     // "Pending", "Approved", "Rejected"
    private String hrMessage;  // Optional message from HR
    
    
    
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] attachmentData;   // stores file content as bytes

    private String attachmentName;   // stores original filename

    // getters and setters for new fields

    
    // Constructors
    public LeaveEntity() {
    }

    public LeaveEntity(String employeeId,String email, String leaveType, LocalDate fromDate, LocalDate toDate,LocalDate appliedDate,String reason, String status, String hrMessage) {
    	 this.employeeId = employeeId;
    	this.email = email;
        this.leaveType = leaveType;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.reason = reason;
        this.status = status;
        this.hrMessage = hrMessage;
    }

    // Getters & Setters

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
    
    public LocalDate getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(LocalDate appliedDate) {
        this.appliedDate = appliedDate;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHrMessage() {
        return hrMessage;
    }

    public void setHrMessage(String hrMessage) {
        this.hrMessage = hrMessage;
    }
    public byte[] getAttachmentData() {
        return attachmentData;
    }

    public void setAttachmentData(byte[] attachmentData) {
        this.attachmentData = attachmentData;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

}
