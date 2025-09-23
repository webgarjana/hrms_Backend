package com.empsys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.empsys.entity.LeaveEntity;

public interface LeaveRepository extends JpaRepository<LeaveEntity, Long> {
    List<LeaveEntity> findByEmail(String email);
    List<LeaveEntity> findByStatus(String status); // for HR view
}
