


package com.empsys.controller;

import com.empsys.dao.OtherClaimRepository;
import com.empsys.dao.NotificationRepository;
import com.empsys.entity.OtherClaimEntity;
import com.empsys.entity.NotificationEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/other-claims")
@CrossOrigin(origins = "http://localhost:3000")
public class OtherClaimController {

    @Autowired
    private OtherClaimRepository repo;

    @Autowired
    private NotificationRepository notificationRepo;

    // ✅ 1. Submit a new claim → HR gets notified
    @PostMapping
    public ResponseEntity<String> saveOtherClaim(
            @RequestParam("employeeId") String employeeId,
            @RequestParam("email") String email,
            @RequestParam("name") String name,
            @RequestParam("title") String title,
            @RequestParam("incidentDate") String incidentDate,
            @RequestParam("description") String description,
            @RequestParam("amount") String amount,
            @RequestParam("status") String status,
            @RequestParam(value = "approvedBy", required = false) String approvedBy,
            @RequestParam(value = "payoutDate", required = false) String payoutDate,
            @RequestParam(value = "proof", required = false) MultipartFile proof
    ) {
        try {
            OtherClaimEntity claim = new OtherClaimEntity();
            claim.setEmployeeId(employeeId);
            claim.setEmail(email);
            claim.setName(name);
            claim.setTitle(title);
            claim.setIncidentDate(incidentDate);
            claim.setDescription(description);
            claim.setAmount(amount);
            claim.setStatus(status);
            claim.setApprovedBy(approvedBy);
            claim.setPayoutDate(payoutDate);

            if (proof != null && !proof.isEmpty()) {
                claim.setProofFileName(proof.getOriginalFilename());
                claim.setProofFile(proof.getBytes());
            }

            repo.save(claim);

            // ✅ Notify HR
            NotificationEntity notification = new NotificationEntity();
            notification.setSenderEmail(email);
            notification.setReceiverEmail("hr@gmail.com");
            notification.setMessage("New other claim submitted by " + name + " (" + employeeId + ")");
            notification.setSeen(false);
            notification.setTimestamp(LocalDateTime.now());
            notificationRepo.save(notification);

            return ResponseEntity.ok("✅ Other claim saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("❌ Failed to save other claim: " + e.getMessage());
        }
    }

    // ✅ 2. Get all claims (HR Dashboard)
    @GetMapping
    public ResponseEntity<List<OtherClaimEntity>> getAllClaims() {
        return ResponseEntity.ok(repo.findAll());
    }

    // ✅ 3. Download proof file
    @GetMapping("/proof/{id}")
    public ResponseEntity<byte[]> downloadProof(@PathVariable Long id) {
        Optional<OtherClaimEntity> opt = repo.findById(id);
        if (opt.isPresent() && opt.get().getProofFile() != null) {
            OtherClaimEntity c = opt.get();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDisposition(ContentDisposition.attachment()
                    .filename(c.getProofFileName()).build());
            return new ResponseEntity<>(c.getProofFile(), headers, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    // ✅ 4. Approve claim → notify employee
    @PutMapping("/approve/{id}")
    public ResponseEntity<String> approveClaim(@PathVariable Long id) {
        Optional<OtherClaimEntity> opt = repo.findById(id);
        if (opt.isPresent()) {
            OtherClaimEntity claim = opt.get();
            claim.setStatus("Approved");
            claim.setApprovedBy("HR Manager");
            claim.setPayoutDate(java.time.LocalDate.now().toString());
            repo.save(claim);

            // ✅ Notify employee
            NotificationEntity notification = new NotificationEntity();
            notification.setSenderEmail("hr@gmail.com");
            notification.setReceiverEmail(claim.getEmail());
            notification.setMessage("Your other claim has been Approved");
            notification.setSeen(false);
            notification.setTimestamp(LocalDateTime.now());
            notificationRepo.save(notification);

            return ResponseEntity.ok("✅ Claim approved");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ Claim not found");
    }

    // ✅ 5. Reject claim → notify employee
    @PutMapping("/reject/{id}")
    public ResponseEntity<String> rejectClaim(@PathVariable Long id) {
        Optional<OtherClaimEntity> opt = repo.findById(id);
        if (opt.isPresent()) {
            OtherClaimEntity claim = opt.get();
            claim.setStatus("Rejected");
            claim.setApprovedBy("HR Manager");
            claim.setPayoutDate("-");
            repo.save(claim);

            // ✅ Notify employee
            NotificationEntity notification = new NotificationEntity();
            notification.setSenderEmail("hr@gmail.com");
            notification.setReceiverEmail(claim.getEmail());
            notification.setMessage("Your other claim has been Rejected");
            notification.setSeen(false);
            notification.setTimestamp(LocalDateTime.now());
            notificationRepo.save(notification);

            return ResponseEntity.ok("✅ Claim rejected");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ Claim not found");
    }

    // ✅ 6. Get claims by employeeId
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<OtherClaimEntity>> getClaimsByEmployee(@PathVariable String employeeId) {
        List<OtherClaimEntity> claims = repo.findByEmployeeId(employeeId);
        return ResponseEntity.ok(claims);
    }
}
