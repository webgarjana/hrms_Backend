package com.empsys.dao;




import org.springframework.data.jpa.repository.JpaRepository;

import com.empsys.entity.BankDetailsEntity;

public interface BankDetailsRepository extends JpaRepository<BankDetailsEntity, Long> {
}

