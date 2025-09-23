package com.empsys.entity;

import javax.persistence.*;

@Entity
@Table(name = "employee_personal_info")
public class EmployeePersonalInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String gender;
    private String maritalStatus;
    private String bloodGroup;
    private String phone;
    private String aadhar;

    @Column(columnDefinition = "TEXT")
    private String profileImageUrl;

    @Lob
    @Column(name = "profile_image_blob")
    private byte[] profileImageBlob;

    private String profileImageBlobfilename;


    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getMaritalStatus() { return maritalStatus; }
    public void setMaritalStatus(String maritalStatus) { this.maritalStatus = maritalStatus; }

    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAadhar() { return aadhar; }
    public void setAadhar(String aadhar) { this.aadhar = aadhar; }

    public String getProfileImageUrl() { return profileImageUrl; }
    public void setProfileImageUrl(String profileImageUrl) { this.profileImageUrl = profileImageUrl; }

    public byte[] getProfileImageBlob() { return profileImageBlob; }
    public void setProfileImageBlob(byte[] profileImageBlob) { this.profileImageBlob = profileImageBlob; }

    public String getProfileImageBlobfilename() {
        return profileImageBlobfilename;
    }

    public void setProfileImageBlobfilename(String profileImageBlobfilename) {
        this.profileImageBlobfilename = profileImageBlobfilename;
    }


}