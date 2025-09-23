

package com.empsys.entity;

import javax.persistence.*;

@Entity
@Table(name = "onboarding")
public class OnboardingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String role = "employee";
    private boolean active = true;

    private boolean firstLogin = true; // ✅ New field added

    @Column(name = "employee_id", unique = true)
    private String employeeId;

    private String fullName;

    @Column(unique = true)
    private String phone;

    @Column(unique = true)
    private String aadharNumber;

    private String department;
    private String joiningDate;
    private String offerDate;

    private boolean resume;
    private boolean idProof;
    private boolean certificates;

    private String probationStart;
    private String probationDuration;

    @Lob
    @Column(name = "offer_letter_blob", columnDefinition = "LONGBLOB")
    private byte[] offerLetterBlob;

    @Column(name = "offer_letter_name")
    private String offerLetterName;

    // ✅ NEW: Additional Personal & Document Fields
    private String fatherName;
    private String motherName;
    private String dob;

    @Lob private byte[] image;
    @Lob private byte[] sign;
    @Lob private byte[] adharCard;
    @Lob private byte[] panCard;
    @Lob private byte[] highschoolCert;
    @Lob private byte[] graduationCert;
    @Lob private byte[] higherQualCert;
    @Lob private byte[] experienceCert;
    @Lob private byte[] addressProof;
    @Lob private byte[] bankPassbook;

    // ----------------- Getters & Setters -----------------

    public Long getId() { return id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public boolean isFirstLogin() { return firstLogin; }
    public void setFirstLogin(boolean firstLogin) { this.firstLogin = firstLogin; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAadharNumber() { return aadharNumber; }
    public void setAadharNumber(String aadharNumber) { this.aadharNumber = aadharNumber; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getJoiningDate() { return joiningDate; }
    public void setJoiningDate(String joiningDate) { this.joiningDate = joiningDate; }

    public String getOfferDate() { return offerDate; }
    public void setOfferDate(String offerDate) { this.offerDate = offerDate; }

    public boolean isResume() { return resume; }
    public void setResume(boolean resume) { this.resume = resume; }

    public boolean isIdProof() { return idProof; }
    public void setIdProof(boolean idProof) { this.idProof = idProof; }

    public boolean isCertificates() { return certificates; }
    public void setCertificates(boolean certificates) { this.certificates = certificates; }

    public String getProbationStart() { return probationStart; }
    public void setProbationStart(String probationStart) { this.probationStart = probationStart; }

    public String getProbationDuration() { return probationDuration; }
    public void setProbationDuration(String probationDuration) { this.probationDuration = probationDuration; }

    public byte[] getOfferLetterBlob() { return offerLetterBlob; }
    public void setOfferLetterBlob(byte[] offerLetterBlob) { this.offerLetterBlob = offerLetterBlob; }

    public String getOfferLetterName() { return offerLetterName; }
    public void setOfferLetterName(String offerLetterName) { this.offerLetterName = offerLetterName; }

    public String getFatherName() { return fatherName; }
    public void setFatherName(String fatherName) { this.fatherName = fatherName; }

    public String getMotherName() { return motherName; }
    public void setMotherName(String motherName) { this.motherName = motherName; }

    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }

    public byte[] getImage() { return image; }
    public void setImage(byte[] image) { this.image = image; }

    public byte[] getSign() { return sign; }
    public void setSign(byte[] sign) { this.sign = sign; }

    public byte[] getAdharCard() { return adharCard; }
    public void setAdharCard(byte[] adharCard) { this.adharCard = adharCard; }

    public byte[] getPanCard() { return panCard; }
    public void setPanCard(byte[] panCard) { this.panCard = panCard; }

    public byte[] getHighschoolCert() { return highschoolCert; }
    public void setHighschoolCert(byte[] highschoolCert) { this.highschoolCert = highschoolCert; }

    public byte[] getGraduationCert() { return graduationCert; }
    public void setGraduationCert(byte[] graduationCert) { this.graduationCert = graduationCert; }

    public byte[] getHigherQualCert() { return higherQualCert; }
    public void setHigherQualCert(byte[] higherQualCert) { this.higherQualCert = higherQualCert; }

    public byte[] getExperienceCert() { return experienceCert; }
    public void setExperienceCert(byte[] experienceCert) { this.experienceCert = experienceCert; }

    public byte[] getAddressProof() { return addressProof; }
    public void setAddressProof(byte[] addressProof) { this.addressProof = addressProof; }

    public byte[] getBankPassbook() { return bankPassbook; }
    public void setBankPassbook(byte[] bankPassbook) { this.bankPassbook = bankPassbook; }
}

