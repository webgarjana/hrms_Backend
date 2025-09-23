package com.empsys.dao;

import com.empsys.entity.PayslipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PayslipRepository extends JpaRepository<PayslipEntity, Long> {
    List<PayslipEntity> findByEmail(String email);
}
