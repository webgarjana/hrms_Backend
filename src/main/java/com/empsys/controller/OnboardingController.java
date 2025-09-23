


package com.empsys.controller;

import com.empsys.dao.OnboardingRepository;
import com.empsys.dto.LoginRequest;
import com.empsys.entity.OnboardingEntity;
import com.empsys.service.MailService;
import com.empsys.service.OnboardingService;
import com.empsys.service.OtpService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/api/onboarding")
@CrossOrigin(origins = "http://localhost:3000")
public class OnboardingController {

    @Autowired
    private OnboardingRepository repository;

    @Autowired
    private OnboardingService onboardingService;
    
    @Autowired
   private OtpService otpService;

    @Autowired
    private MailService mailService; // ✅ Added for sending welcome email

    // ✅ Save onboarding with file upload and uniqueness validation
    @PostMapping("/save")
    public ResponseEntity<?> saveOnboarding(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("role") String role,
            @RequestParam("employeeId") String employeeId,
            @RequestParam("fullName") String fullName,
            @RequestParam("phone") String phone,
            @RequestParam("department") String department,
            @RequestParam("joiningDate") String joiningDate,
            @RequestParam("offerDate") String offerDate,
            @RequestParam("probationStart") String probationStart,
            @RequestParam("probationDuration") String probationDuration,
            @RequestParam("resume") boolean resume,
            @RequestParam("idProof") boolean idProof,
            @RequestParam("certificates") boolean certificates,
            @RequestParam("offerLetter") MultipartFile offerLetter,
            @RequestParam("aadharNumber") String aadharNumber
    ) {
        try {
            // ✅ Uniqueness check
            if (repository.existsByEmail(email)) return ResponseEntity.badRequest().body("Email already exists!");
            if (repository.existsByPhone(phone)) return ResponseEntity.badRequest().body("Phone number already exists!");
            if (repository.existsByAadharNumber(aadharNumber)) return ResponseEntity.badRequest().body("Aadhar number already exists!");
            if (repository.existsByEmployeeId(employeeId)) return ResponseEntity.badRequest().body("Employee ID already exists!");

            OnboardingEntity entity = new OnboardingEntity();
            entity.setEmail(email);
            entity.setPassword(password);
            entity.setRole(role);
            entity.setEmployeeId(employeeId);
            entity.setFullName(fullName);
            entity.setPhone(phone);
            entity.setDepartment(department);
            entity.setJoiningDate(joiningDate);
            entity.setOfferDate(offerDate);
            entity.setProbationStart(probationStart);
            entity.setProbationDuration(probationDuration);
            entity.setResume(resume);
            entity.setIdProof(idProof);
            entity.setCertificates(certificates);
            entity.setAadharNumber(aadharNumber);
            entity.setActive(true);

            if (!offerLetter.isEmpty()) {
                entity.setOfferLetterName(offerLetter.getOriginalFilename());
                entity.setOfferLetterBlob(offerLetter.getBytes());
            }

            repository.save(entity);

            // ✅ Send welcome email (optional, won't break onboarding if fails)
            try {
                mailService.sendWelcomeEmail(email, fullName, employeeId, password);
            } catch (Exception ex) {
                ex.printStackTrace();
                System.err.println("❌ Email failed: " + ex.getMessage());
            }

            return ResponseEntity.ok("✅ Employee onboarded successfully and email sent!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("❌ Failed to save file");
        }
    }

    // ✅ Login
//    @PostMapping("/login")
//    public Optional<OnboardingEntity> login(@RequestBody LoginRequest request) {
//        return repository.findByEmailAndPasswordAndRoleAndActiveTrue(
//                request.getEmail(),
//                request.getPassword(),
//                request.getRole()
//        );
//    }
    
 // ✅ Enhanced Login with firstLogin tracking
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> data) {
        String email = data.get("email");
        String password = data.get("password");

        Optional<OnboardingEntity> optional = onboardingService.loginEmployee(email, password);
        if (optional.isPresent()) {
            OnboardingEntity emp = optional.get();
            Map<String, Object> response = new HashMap<>();
            response.put("email", emp.getEmail());
            response.put("employeeId", emp.getEmployeeId());
            response.put("firstLogin", emp.isFirstLogin());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }


    // ✅ Get all
    @GetMapping("/all")
    public List<OnboardingEntity> getAll() {
        return repository.findAll();
    }

    // ✅ Get last employeeId
    @GetMapping("/last-id")
    public String getLastEmployeeId() {
        List<OnboardingEntity> list = repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        return !list.isEmpty() ? list.get(0).getEmployeeId() : "WG0000";
    }

    // ✅ Get by email
    @GetMapping("/email/{email}")
    public Optional<OnboardingEntity> getByEmail(@PathVariable String email) {
        return repository.findByEmail(email);
    }

    // ✅ Get employeeId by email
    @GetMapping("/employee-id")
    public ResponseEntity<String> getEmployeeIdByEmail(@RequestParam String email) {
        Optional<OnboardingEntity> employee = repository.findByEmail(email);
        return employee.map(e -> ResponseEntity.ok(e.getEmployeeId()))
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Get email by employeeId
    @GetMapping("/email")
    public ResponseEntity<String> getEmailByEmployeeId(@RequestParam String employeeId) {
        String email = onboardingService.getEmailByEmployeeId(employeeId);
        return email != null ? ResponseEntity.ok(email) : ResponseEntity.notFound().build();
    }

    // ✅ Download offer letter
    @GetMapping("/offer-letter/{id}")
    public ResponseEntity<byte[]> downloadOfferLetter(@PathVariable Long id) {
        Optional<OnboardingEntity> optional = repository.findById(id);
        if (optional.isPresent()) {
            OnboardingEntity entity = optional.get();
            byte[] fileData = entity.getOfferLetterBlob();

            if (fileData != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDisposition(ContentDisposition.attachment()
                        .filename(entity.getOfferLetterName()).build());
                return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // ✅ Upload personal documents
    @PostMapping("/update-docs/{id}")
    public ResponseEntity<String> updateDocs(
            @PathVariable Long id,
            @RequestParam(required = false) String fatherName,
            @RequestParam(required = false) String motherName,
            @RequestParam(required = false) String dob,
            @RequestParam(required = false) MultipartFile image,
            @RequestParam(required = false) MultipartFile sign,
            @RequestParam(required = false) MultipartFile adharCard,
            @RequestParam(required = false) MultipartFile panCard,
            @RequestParam(required = false) MultipartFile highschoolCert,
            @RequestParam(required = false) MultipartFile graduationCert,
            @RequestParam(required = false) MultipartFile higherQualCert,
            @RequestParam(required = false) MultipartFile experienceCert,
            @RequestParam(required = false) MultipartFile addressProof,
            @RequestParam(required = false) MultipartFile bankPassbook
    ) {
        Optional<OnboardingEntity> optional = repository.findById(id);
        if (!optional.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");

        OnboardingEntity entity = optional.get();
        if (fatherName != null) entity.setFatherName(fatherName);
        if (motherName != null) entity.setMotherName(motherName);
        if (dob != null) entity.setDob(dob);

        try {
            if (image != null) entity.setImage(image.getBytes());
            if (sign != null) entity.setSign(sign.getBytes());
            if (adharCard != null) entity.setAdharCard(adharCard.getBytes());
            if (panCard != null) entity.setPanCard(panCard.getBytes());
            if (highschoolCert != null) entity.setHighschoolCert(highschoolCert.getBytes());
            if (graduationCert != null) entity.setGraduationCert(graduationCert.getBytes());
            if (higherQualCert != null) entity.setHigherQualCert(higherQualCert.getBytes());
            if (experienceCert != null) entity.setExperienceCert(experienceCert.getBytes());
            if (addressProof != null) entity.setAddressProof(addressProof.getBytes());
            if (bankPassbook != null) entity.setBankPassbook(bankPassbook.getBytes());

            repository.save(entity);
            return ResponseEntity.ok("✅ Documents updated");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("❌ Failed to save documents");
        }
    }

    // ✅ View document by type
    @GetMapping("/view-doc/{id}/{type}")
    public ResponseEntity<byte[]> viewDocument(@PathVariable Long id, @PathVariable String type) {
        Optional<OnboardingEntity> optional = repository.findById(id);
        if (!optional.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        OnboardingEntity entity = optional.get();
        byte[] doc = null;
        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;

        switch (type.toLowerCase()) {
            case "image": doc = entity.getImage(); mediaType = MediaType.IMAGE_JPEG; break;
            case "sign": doc = entity.getSign(); mediaType = MediaType.IMAGE_JPEG; break;
            case "adharcard": doc = entity.getAdharCard(); mediaType = MediaType.APPLICATION_PDF; break;
            case "pancard": doc = entity.getPanCard(); mediaType = MediaType.APPLICATION_PDF; break;
            case "highschoolcert": doc = entity.getHighschoolCert(); mediaType = MediaType.APPLICATION_PDF; break;
            case "graduationcert": doc = entity.getGraduationCert(); mediaType = MediaType.APPLICATION_PDF; break;
            case "higherqualcert": doc = entity.getHigherQualCert(); mediaType = MediaType.APPLICATION_PDF; break;
            case "experiencecert": doc = entity.getExperienceCert(); mediaType = MediaType.APPLICATION_PDF; break;
            case "addressproof": doc = entity.getAddressProof(); mediaType = MediaType.APPLICATION_PDF; break;
            case "bankpassbook": doc = entity.getBankPassbook(); mediaType = MediaType.APPLICATION_PDF; break;
        }

        if (doc == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        headers.setContentDisposition(ContentDisposition.inline().filename(type + ".pdf").build());
        return new ResponseEntity<>(doc, headers, HttpStatus.OK);
    }
    
    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> data) {
        String email = data.get("email");
        String newPassword = data.get("newPassword");

        Optional<OnboardingEntity> optional = onboardingService.getByEmail(email);
        if (optional.isPresent()) {
            OnboardingEntity emp = optional.get();
            emp.setPassword(newPassword);
            emp.setFirstLogin(false);
            onboardingService.saveOnboarding(emp);
            return ResponseEntity.ok("Password reset successful");
        } else {
            return ResponseEntity.badRequest().body("Employee not found");
        }
    }
    
    
    
  
//  //forget
    @PostMapping("/forget-password")
    public ResponseEntity<String> forgetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        Optional<OnboardingEntity> optionalUser = repository.findByEmail(email);

        if (optionalUser.isPresent()) {
            String otp = otpService.generateOtp(email); // ✅ Add this line to generate OTP

            String message = "Your OTP to reset your password is: " + otp + "\n\n" ;
                    

            mailService.sendResetEmail(email, message); // ✅ Send message using MailService

            return ResponseEntity.ok("Reset password instructions sent to your email.");
        } else {
            return ResponseEntity.ok("Email not found in system.");
        }
    }
    
    
  //change-password
    
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody Map<String, String> data) {
        String newPassword = data.get("newPassword");

        // Get email from logged-in user
       // String email = principal.getName();
        String email = data.get("email");
        Optional<OnboardingEntity> optional = onboardingService.getByEmail(email);
        if (optional.isPresent()) {
            OnboardingEntity emp = optional.get();
            emp.setPassword(newPassword);
            emp.setFirstLogin(false);
            onboardingService.saveOnboarding(emp);
            return ResponseEntity.ok("Password changed successfully");
        } else {
            return ResponseEntity.badRequest().body("Employee not found");
        }
    }



}

