package com.empsys.controller;

import com.empsys.entity.OfficialInfoEntity;
import com.empsys.service.OfficialInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/official-info")
@CrossOrigin(origins = "http://localhost:3000")
public class OfficialInfoController {

    @Autowired
    private OfficialInfoService officialInfoService;

    @PostMapping("/save")
    public ResponseEntity<?> saveOrUpdate(@RequestBody OfficialInfoEntity info) {
        Optional<OfficialInfoEntity> existingData = officialInfoService.getOfficialInfoByEmployeeId(info.getEmployeeId());

        OfficialInfoEntity saved;

        if (existingData.isPresent()) {
            // 👇 Update existing record
            OfficialInfoEntity existing = existingData.get();
            existing.setEmployeeName(info.getEmployeeName());
            existing.setEmail(info.getEmail());
            existing.setDepartment(info.getDepartment());
            existing.setDesignation(info.getDesignation());
            existing.setManager(info.getManager());
            existing.setTeamName(info.getTeamName());
            existing.setAssignmentDate(info.getAssignmentDate());
            existing.setRemarks(info.getRemarks());

            saved = officialInfoService.saveOfficialInfo(existing);
        } else {
            // 👇 Save new record
            saved = officialInfoService.saveOfficialInfo(info);
        }

        return ResponseEntity.ok(saved);
    }

//    @GetMapping("/get/{employeeId}")
//    public ResponseEntity<?> getInfo(@PathVariable String employeeId) {
//        Optional<OfficialInfoEntity> data = officialInfoService.getOfficialInfoByEmployeeId(employeeId);
//
//        if (data.isPresent()) {
//            return ResponseEntity.ok(data.get());
//        } else {
//            return ResponseEntity.status(404).body("Official info not found for employeeId: " + employeeId);
//        }
//    }
    
    
    
    @GetMapping("/get/{employeeId}")
    public ResponseEntity<?> getInfo(@PathVariable String employeeId) {
        Optional<OfficialInfoEntity> data = officialInfoService.getOfficialInfoByEmployeeId(employeeId);

        if (data.isPresent()) {
            return ResponseEntity.ok(data.get());
        } else {
            return ResponseEntity.status(404)
                    .body(Map.of("error", "Official info not found for employeeId: " + employeeId));
        }
    }

}

