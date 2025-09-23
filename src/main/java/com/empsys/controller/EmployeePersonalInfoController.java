package com.empsys.controller;

import com.empsys.dao.EmployeePersonalInfoRepository;
import com.empsys.entity.EmployeePersonalInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/personal-info")
public class EmployeePersonalInfoController {

    @Autowired
    private EmployeePersonalInfoRepository repository;

    // ✅ Save info (POST with multipart for file)


    @PostMapping("/save-multipart")
    public ResponseEntity<String> saveWithImage(
            @RequestParam("fullName") String fullName,
            @RequestParam("email") String email,
            @RequestParam("gender") String gender,
            @RequestParam("maritalStatus") String maritalStatus,
            @RequestParam("bloodGroup") String bloodGroup,
            @RequestParam("phone") String phone,
            @RequestParam("aadhar") String aadhar,
            @RequestParam(value = "profileImageBlob", required = false) MultipartFile profileImageBlob
    ) {
        try {
            EmployeePersonalInfoEntity info = new EmployeePersonalInfoEntity();
            info.setFullName(fullName);
            info.setEmail(email);
            info.setGender(gender);
            info.setMaritalStatus(maritalStatus);
            info.setBloodGroup(bloodGroup);
            info.setPhone(phone);
            info.setAadhar(aadhar);

            if (profileImageBlob != null && !profileImageBlob.isEmpty()) {
                info.setProfileImageBlob(profileImageBlob.getBytes());
                info.setProfileImageBlobfilename(profileImageBlob.getOriginalFilename());
            }

            repository.save(info);
            return ResponseEntity.ok("✅ Saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("❌ Error: " + e.getMessage());
        }
    }



    // ✅ Get info by email
    @GetMapping("/get/{email}")
    public ResponseEntity<?> getPersonalInfo(@PathVariable String email) {
        Optional<EmployeePersonalInfoEntity> optional = repository.findByEmail(email);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Download image by email
    @GetMapping("/image/{email}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable String email) {
        Optional<EmployeePersonalInfoEntity> optional = repository.findByEmail(email);
        if (optional.isPresent() && optional.get().getProfileImageBlob() != null) {
            byte[] fileData = optional.get().getProfileImageBlob();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // or detect from filename
            headers.setContentDisposition(ContentDisposition.attachment()
                    .filename(optional.get().getProfileImageBlobfilename())
                    .build());
            return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/profile-image/{email}")
    public ResponseEntity<byte[]> getImageByEmail(@PathVariable String email) {
        Optional<EmployeePersonalInfoEntity> optional = repository.findByEmail(email);
        if (optional.isPresent() && optional.get().getProfileImageBlob() != null) {
            EmployeePersonalInfoEntity info = optional.get();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentDisposition(ContentDisposition.inline().filename(info.getProfileImageBlobfilename()).build());

            return new ResponseEntity<>(info.getProfileImageBlob(), headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/get-by-email/{email}")
    public ResponseEntity<EmployeePersonalInfoEntity> getByEmail(@PathVariable String email) {
        return repository.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
 

}