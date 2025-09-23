package com.empsys.dao;

import com.empsys.entity.OfficialInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OfficialInfoRepository extends JpaRepository<OfficialInfoEntity, Long> {
    Optional<OfficialInfoEntity> findByEmployeeId(String employeeId);
}