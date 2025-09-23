package com.empsys.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.empsys.entity.OtpEntity;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<OtpEntity, Long> {
    Optional<OtpEntity> findByEmail(String email);
    void deleteByEmail(String email);
}
