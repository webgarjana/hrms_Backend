package com.empsys.entity;

import javax.persistence.*;

@Entity
@Table(name = "medical_claims")
public class MedicalClaimEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long claimId;

    private String employeeId;
    private String email;
    private String name;
   
    private String medicalType;
    private String treatmentDate;
    private String claimAmount;
    private String doctorName;
    private String remarks;
    private String policyNumber;
    private String uploadedFile;
    private String status;
    private String submittedOn;
    private String approvedOn;
    private String approvedBy;
    private String fileDatafileData;

    @Lob
    @Column(name = "file_data", columnDefinition = "LONGBLOB")
    private byte[] fileData;

    // Getters and Setters
    

    public Long getClaimId() { return claimId; }
    public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setClaimId(Long claimId) { this.claimId = claimId; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

   
    public String getMedicalType() { return medicalType; }
    public void setMedicalType(String medicalType) { this.medicalType = medicalType; }

    public String getTreatmentDate() { return treatmentDate; }
    public void setTreatmentDate(String treatmentDate) { this.treatmentDate = treatmentDate; }

    public String getClaimAmount() { return claimAmount; }
    public void setClaimAmount(String claimAmount) { this.claimAmount = claimAmount; }

    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String getPolicyNumber() { return policyNumber; }
    public void setPolicyNumber(String policyNumber) { this.policyNumber = policyNumber; }

    public String getUploadedFile() { return uploadedFile; }
    public void setUploadedFile(String uploadedFile) { this.uploadedFile = uploadedFile; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getSubmittedOn() { return submittedOn; }
    public void setSubmittedOn(String submittedOn) { this.submittedOn = submittedOn; }

    public String getApprovedOn() { return approvedOn; }
    public void setApprovedOn(String approvedOn) { this.approvedOn = approvedOn; }

    public String getApprovedBy() { return approvedBy; }
    public void setApprovedBy(String approvedBy) { this.approvedBy = approvedBy; }

    public byte[] getFileData() { return fileData; }
    public void setFileData(byte[] fileData) { this.fileData = fileData; }
	public void setHrMessage(String hrMessage) {
		// TODO Auto-generated method stub
		
	}
}