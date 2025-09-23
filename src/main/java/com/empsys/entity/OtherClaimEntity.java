package com.empsys.entity;

import javax.persistence.*;

@Entity
@Table(name = "other_claims")
public class OtherClaimEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long claimId;

    private String employeeId;
    private String email;
    private String name;
    
    private String title;
    private String incidentDate;
    private String description;
    private String amount;
   
    private String status;
    private String approvedBy;
    private String payoutDate;
    private String proofFileName;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] proofFile;

    // Constructors
    public OtherClaimEntity() {}

    public OtherClaimEntity(Long claimId, String employeeId,String email, String name, String designation, String title,
                            String incidentDate, String description, String amount, String submittedOn,
                            String status, String approvedBy, String payoutDate,
                            String proofFileName, byte[] proofFile) {
        this.claimId = claimId;
        this.employeeId = employeeId;
        this.email = email;
        this.name = name;
       
        this.title = title;
        this.incidentDate = incidentDate;
        this.description = description;
        this.amount = amount;
       
        this.status = status;
        this.approvedBy = approvedBy;
        this.payoutDate = payoutDate;
        this.proofFileName = proofFileName;
        this.proofFile = proofFile;
    }

    // Getters and Setters
    public Long getClaimId() {
        return claimId;
    }

    public void setClaimId(Long claimId) {
        this.claimId = claimId;
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

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIncidentDate() {
        return incidentDate;
    }

    public void setIncidentDate(String incidentDate) {
        this.incidentDate = incidentDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getPayoutDate() {
        return payoutDate;
    }

    public void setPayoutDate(String payoutDate) {
        this.payoutDate = payoutDate;
    }

    public String getProofFileName() {
        return proofFileName;
    }

    public void setProofFileName(String proofFileName) {
        this.proofFileName = proofFileName;
    }

    public byte[] getProofFile() {
        return proofFile;
    }

    public void setProofFile(byte[] proofFile) {
        this.proofFile = proofFile;
    }
}