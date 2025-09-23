package com.empsys.dao;








import com.empsys.entity.MedicalClaimEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalClaimRepository extends JpaRepository<MedicalClaimEntity, Long> {
	// In MedicalClaimRepository.java
	List<MedicalClaimEntity> findByEmployeeId(String employeeId);

}