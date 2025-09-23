package com.empsys.service;

import com.empsys.dao.EducationDetailsRepository;
import com.empsys.dto.EducationDetailsDTO;
import com.empsys.entity.EducationDetailsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class EducationDetailsService {

    @Autowired
    private EducationDetailsRepository educationDetailsRepository;

    @Transactional
    public void saveEducationDetails(EducationDetailsDTO dto) {
        List<EducationDetailsEntity> entities = new ArrayList<>();
        for (EducationDetailsDTO.EducationDetailsItem item : dto.getEducationList()) {
            EducationDetailsEntity edu = new EducationDetailsEntity();
            edu.setEmail(dto.getEmail());
            edu.setQualification(item.getQualification());
            edu.setMajor(item.getMajor());
            edu.setInstitute(item.getInstitute());
            edu.setUniversity(item.getUniversity());
            edu.setYear(item.getYear());
            edu.setCgpa(item.getCgpa());
            edu.setType(item.getType());
            edu.setCertifications(item.getCertifications());
            entities.add(edu);
        }

        educationDetailsRepository.saveAll(entities); // ✅ No delete, only append
    }

    public List<EducationDetailsEntity> getEducationByEmail(String email) {
        return educationDetailsRepository.findByEmail(email);
    }

    public List<EducationDetailsEntity> getAll() {
        return educationDetailsRepository.findAll();
    }
}