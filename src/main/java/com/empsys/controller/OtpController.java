

package com.empsys.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.empsys.service.OtpService;

@RestController
@RequestMapping("/api/otp")
@CrossOrigin(origins = "http://localhost:3000") // allow React frontend
public class OtpController {

    @Autowired
    private OtpService otpService;

    // Endpoint to generate OTP
    @PostMapping("/generate")
    public String generateOtp(@RequestParam String email) {
        String otp = otpService.generateOtp(email);
        // In real scenario, send OTP via email/SMS instead of returning it
        return "OTP generated and sent to email. (Debug: " + otp + ")";
    }

    // Endpoint to verify OTP
    @GetMapping("/verify")
    public ResponseEntity<String> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        if (otpService.verifyOtp(email, otp)) {
            otpService.clearOtp(email); // now runs in a transaction
            return ResponseEntity.ok("OTP Verified Successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP");
        }
    }

}
