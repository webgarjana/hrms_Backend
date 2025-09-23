
package com.empsys.dao;

import com.empsys.entity.OnboardingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OnboardingRepository extends JpaRepository<OnboardingEntity, Long> {

    // ✅ Original methods
    Optional<OnboardingEntity> findByEmail(String email);
    Optional<OnboardingEntity> findByEmailAndPasswordAndActiveTrue(String email, String password);
    Optional<OnboardingEntity> findByEmailAndPasswordAndRoleAndActiveTrue(String email, String password, String role);
    Optional<OnboardingEntity> findByEmployeeId(String employeeId);

    // ✅ Additional exists checks
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByEmployeeId(String employeeId);
    boolean existsByAadharNumber(String aadharNumber);

    // ✅ Active and role queries
    List<OnboardingEntity> findByActiveTrueAndRole(String role);
    List<String> findEmailsByRoleAndActive(String role, boolean active);

    // ✅ New delete methods
    void deleteByEmail(String email);
    void deleteByEmployeeId(String employeeId);
}
