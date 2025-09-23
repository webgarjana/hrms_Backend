package com.empsys.dao;

import com.empsys.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {
    List<DocumentEntity> findByEmail(String email);

    Optional<DocumentEntity> findByEmailAndFileName(String email, String fileName);
    Optional<DocumentEntity> findByFileName(String fileName);
}
