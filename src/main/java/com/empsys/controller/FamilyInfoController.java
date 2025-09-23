package com.empsys.controller;

import com.empsys.dao.FamilyInfoRepository;
import com.empsys.entity.FamilyInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/family")
@CrossOrigin(origins = "*")
public class FamilyInfoController {

    @Autowired
    private FamilyInfoRepository familyInfoRepository;

    // 🔄 Save or Update

    @PostMapping("/save")
    public String saveOrUpdateFamilyInfo(@RequestBody FamilyInfoEntity info) {
        if (info.getEmail() == null || info.getEmail().isEmpty()) {
            return "❌ Email is missing.";
        }

        List<FamilyInfoEntity> existingList = familyInfoRepository.findByEmail(info.getEmail());
        if (!existingList.isEmpty()) {
            FamilyInfoEntity existing = existingList.get(0);
            existing.setFatherName(info.getFatherName());
            existing.setMotherName(info.getMotherName());
            existing.setEmergencyContactName(info.getEmergencyContactName());
            existing.setEmergencyContactRelation(info.getEmergencyContactRelation());
            existing.setEmergencyContactPhone(info.getEmergencyContactPhone());
            familyInfoRepository.save(existing);
            return "✅ Family info updated";
        } else {
            familyInfoRepository.save(info);
            return "✅ Family info saved";
        }
    }


    // 📥 Get All Family Info
    @GetMapping("/all")
    public List<FamilyInfoEntity> getAll() {
        return familyInfoRepository.findAll();
    }

    // 🔍 Get by employeeId
    @GetMapping("/employee/{employeeId}")
    public List<FamilyInfoEntity> getByEmployeeId(@PathVariable String employeeId) {
        return familyInfoRepository.findByEmployeeId(employeeId);
    }

    // 🔍 Get by email
    @GetMapping("/by-email/{email}")
    public List<FamilyInfoEntity> getByEmail(@PathVariable String email) {
        return familyInfoRepository.findByEmail(email);
    }
}