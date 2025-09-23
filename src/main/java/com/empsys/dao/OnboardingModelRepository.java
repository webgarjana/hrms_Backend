package com.empsys.dao;







import org.springframework.data.jpa.repository.JpaRepository;

import com.empsys.entity.OnboardingModel;

import java.util.Optional;

public interface OnboardingModelRepository extends JpaRepository<OnboardingModel, Long> {
 Optional<OnboardingModel> findByEmail(String email);
 Optional<OnboardingModel> findByEmployeeId(String employeeId);
}