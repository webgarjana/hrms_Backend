package com.empsys.service;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empsys.dao.OnboardingModelRepository;
import com.empsys.entity.OnboardingModel;

import java.util.Optional;

@Service
public class OnboardingModelServices {

    @Autowired
    private OnboardingModelRepository onboardingRepository;

    public String revokeAccessByEmailOrId(String email, String employeeId) {
        Optional<OnboardingModel> optionalUser;

        if (email != null && !email.isBlank()) {
            optionalUser = onboardingRepository.findByEmail(email);
        } else if (employeeId != null && !employeeId.isBlank()) {
            optionalUser = onboardingRepository.findByEmployeeId(employeeId);
        } else {
            return "❌ Email or Employee ID required.";
        }

        if (optionalUser.isPresent()) {
            OnboardingModel user = optionalUser.get();
            user.setActive(false);
            onboardingRepository.save(user);
            return "✅ Access revoked for " + user.getFullName();
        } else {
            return "❌ User not found.";
        }
    }

    // ✅ New method: delete employee from database
    public void deleteEmployeeByEmailOrId(String email, String employeeId) {
        if (email != null && !email.isBlank()) {
            onboardingRepository.findByEmail(email).ifPresent(onboardingRepository::delete);
        } else if (employeeId != null && !employeeId.isBlank()) {
            onboardingRepository.findByEmployeeId(employeeId).ifPresent(onboardingRepository::delete);
        }
    }

}
