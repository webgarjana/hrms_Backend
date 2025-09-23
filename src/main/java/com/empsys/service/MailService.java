package com.empsys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendWelcomeEmail(String toEmail, String fullName, String employeeId, String password) {
        String subject = "🎉 Welcome to Web Garjana Technology";

        String body = "Dear " + fullName + ",\n\n" +
                "Welcome to Web Garjana Technology! 🎉\n\n" +
                "Your onboarding is complete. Please find your login credentials below:\n\n" +
                "🔹 Employee ID: " + employeeId + "\n" +
                "🔹 Login Email: " + toEmail + "\n" +
                "🔹 Temporary Password: " + password + "\n\n" +
                "You can now log in to the Web Garjana employee portal.\n\n" +
                "Regards,\n" +
                "HR Team\n" +
                "Web Garjana Technology";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hr@webgarjana.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

  
//forget
    public void sendResetEmail(String toEmail, String resetMessage) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Password Reset Request");
        message.setText(resetMessage);
        message.setFrom("hr@webgarjana.com"); // same as spring.mail.username

        mailSender.send(message);
    }
    
    
    public void sendOtpEmail(String to, String message) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setSubject("Reset Password OTP");
        mail.setText(message);
        mailSender.send(mail);
    }
	
}