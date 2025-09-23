package com.empsys.dao;

import com.empsys.entity.FamilyInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamilyInfoRepository extends JpaRepository<FamilyInfoEntity, Long> {

    // 🔍 Search by employeeId
    List<FamilyInfoEntity> findByEmployeeId(String employeeId);

    // 🔍 Search by email directly (new)
    List<FamilyInfoEntity> findByEmail(String email);

    // ✅ Optional utility
    boolean existsByEmployeeId(String employeeId);
}