package com.empsys.dao;

import com.empsys.entity.HLoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HLoginRepository extends JpaRepository<HLoginEntity, Long> {
    Optional<HLoginEntity> findByUsername(String username);
}
