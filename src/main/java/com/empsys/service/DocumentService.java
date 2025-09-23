package com.empsys.service;

import com.empsys.entity.DocumentEntity;
import com.empsys.dao.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public DocumentEntity saveDocument(DocumentEntity document) {
        return documentRepository.save(document);
    }

    public List<DocumentEntity> getDocumentsByEmail(String email) {
        return documentRepository.findByEmail(email);
    }

    public Optional<DocumentEntity> getDocumentByFileName(String fileName) {
        return documentRepository.findByFileName(fileName);
    }
}
