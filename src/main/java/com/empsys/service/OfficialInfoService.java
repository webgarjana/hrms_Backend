package com.empsys.service;

import com.empsys.dao.OfficialInfoRepository;
import com.empsys.entity.OfficialInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OfficialInfoService {

    @Autowired
    private OfficialInfoRepository officialInfoRepository;

    // Save or update Official Info based on employeeId
    public OfficialInfoEntity saveOfficialInfo(OfficialInfoEntity newInfo) {
        Optional<OfficialInfoEntity> existing = officialInfoRepository.findByEmployeeId(newInfo.getEmployeeId());

        if (existing.isPresent()) {
            OfficialInfoEntity oldInfo = existing.get();

            // Update fields
            oldInfo.setEmployeeName(newInfo.getEmployeeName());
            oldInfo.setEmail(newInfo.getEmail());
            oldInfo.setDepartment(newInfo.getDepartment());
            oldInfo.setDesignation(newInfo.getDesignation());
            oldInfo.setManager(newInfo.getManager());
            oldInfo.setTeamName(newInfo.getTeamName());
            oldInfo.setAssignmentDate(newInfo.getAssignmentDate());
            oldInfo.setRemarks(newInfo.getRemarks());

            return officialInfoRepository.save(oldInfo); // update
        } else {
            return officialInfoRepository.save(newInfo); // insert
        }
    }

    // Fetch by employeeId
    public Optional<OfficialInfoEntity> getOfficialInfoByEmployeeId(String empId) {
        return officialInfoRepository.findByEmployeeId(empId);
    }
}