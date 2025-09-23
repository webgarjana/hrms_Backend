package com.empsys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // <-- ADD THIS IMPORT

import com.empsys.dao.OtpRepository;
import com.empsys.entity.OtpEntity;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private OtpRepository otpRepository;

    @Transactional // <-- ADD HERE so deleteByEmail() runs in a transaction
    public String generateOtp(String email) {
        String otp = String.format("%06d", new Random().nextInt(999999));
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(5); // OTP valid for 5 mins

        // Remove old OTP if exists
        otpRepository.deleteByEmail(email.trim().toLowerCase());

        // Save new OTP
        OtpEntity otpEntity = new OtpEntity(email.trim().toLowerCase(), otp, expiry);
        otpRepository.save(otpEntity);

        return otp;
    }

    public boolean verifyOtp(String email, String otp) {
        Optional<OtpEntity> otpData = otpRepository.findByEmail(email.trim().toLowerCase());

        if (otpData.isPresent()) {
            OtpEntity entity = otpData.get();

            // Check expiry
            if (LocalDateTime.now().isAfter(entity.getExpiryTime())) {
                return false; // expired
            }

            return entity.getOtp().equals(otp);
        }
        return false;
    }

    @Transactional // <-- ADD HERE so deleteByEmail() works
    public void clearOtp(String email) {
        otpRepository.deleteByEmail(email.trim().toLowerCase());
    }
}
