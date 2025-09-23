package com.empsys.dto;



import lombok.Data;

@Data
public class MedicalClaimStatusUpdateDTO {
    private String status;       // e.g., Approved, Rejected
    private String hrMessage;    // HR remarks
    private String approvedBy;   // HR name
    private String approvedOn;  
    // date string or LocalDateTime
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
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public String getApprovedOn() {
		return approvedOn;
	}
	public void setApprovedOn(String approvedOn) {
		this.approvedOn = approvedOn;
	}
}
