package com.empsys.dao;

import com.empsys.entity.OtherClaimEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OtherClaimRepository extends JpaRepository<OtherClaimEntity, Long> {
    List<OtherClaimEntity> findByEmployeeId(String employeeId);
    
}