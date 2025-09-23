package com.empsys.dao;

import com.empsys.entity.EducationDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationDetailsRepository extends JpaRepository<EducationDetailsEntity, Long> {
    List<EducationDetailsEntity> findByEmail(String email);
}