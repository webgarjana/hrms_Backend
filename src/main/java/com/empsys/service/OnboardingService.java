

package com.empsys.service;

import com.empsys.dao.OnboardingRepository;
import com.empsys.entity.OnboardingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OnboardingService {

    @Autowired
    private OnboardingRepository repository;
    
    @Autowired
    private JavaMailSender mailSender;
    
    

    public OnboardingEntity saveOnboarding(OnboardingEntity entity) {
        return repository.save(entity);
    }

    public Optional<OnboardingEntity> getByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Optional<OnboardingEntity> loginEmployee(String email, String password) {
        return repository.findByEmailAndPasswordAndActiveTrue(email, password);
    }

    public List<OnboardingEntity> getAll() {
        return repository.findAll();
    }

    public String getEmailByEmployeeId(String employeeId) {
        Optional<OnboardingEntity> employee = repository.findByEmployeeId(employeeId);
        return employee.map(OnboardingEntity::getEmail).orElse(null);
    }

    // ✅ Extra methods from second code for validation
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    public boolean existsByPhone(String phone) {
        return repository.existsByPhone(phone);
    }

    public boolean existsByEmployeeId(String employeeId) {
        return repository.existsByEmployeeId(employeeId);
    }

    public boolean existsByAadharNumber(String aadharNumber) {
        return repository.existsByAadharNumber(aadharNumber);
    }
    
    public void markFirstLoginComplete(String email) {
        Optional<OnboardingEntity> optional = repository.findByEmail(email);
        if (optional.isPresent()) {
            OnboardingEntity entity = optional.get();
            entity.setFirstLogin(false);  // 👈 disable the flag
            repository.save(entity);      // ✅ persist the change
        }
    }
    
    
    
//   //forget 
    public void sendResetEmail(String toEmail, String messageBody) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Password Reset Request");
        message.setText(messageBody);
        message.setFrom("hr@webgarjana.com");

        mailSender.send(message);
    }


}

