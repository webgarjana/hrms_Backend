package com.empsys.service;

import com.empsys.entity.EmployeePersonalInfoEntity;

import java.util.Optional;

public interface EmployeePersonalInfoService {
    EmployeePersonalInfoEntity save(EmployeePersonalInfoEntity info);
    Optional<EmployeePersonalInfoEntity> getByEmail(String email);
}