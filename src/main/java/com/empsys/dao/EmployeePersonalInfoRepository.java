package com.empsys.dao;

import com.empsys.entity.EmployeePersonalInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface EmployeePersonalInfoRepository extends JpaRepository<EmployeePersonalInfoEntity, Long> 
{
    Optional<EmployeePersonalInfoEntity> findByEmail(String email);



}