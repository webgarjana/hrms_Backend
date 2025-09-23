package com.empsys.dao;

import com.empsys.entity.ELoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ELoginRepository extends JpaRepository<ELoginEntity, Long> {
    Optional<ELoginEntity> findByUsername(String username);
}
