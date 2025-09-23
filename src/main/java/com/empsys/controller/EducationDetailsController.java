package com.empsys.controller;

import com.empsys.dto.EducationDetailsDTO;
import com.empsys.entity.EducationDetailsEntity;
import com.empsys.service.EducationDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/education")
public class EducationDetailsController {

    @Autowired
    private EducationDetailsService educationDetailsService;

    @PostMapping("/save")
    public ResponseEntity<String> saveEducation(@RequestBody EducationDetailsDTO dto) {
        if (dto.getEducationList() == null || dto.getEducationList().isEmpty()) {
            return ResponseEntity.badRequest().body("Education list is missing or empty");
        }

        educationDetailsService.saveEducationDetails(dto);
        return ResponseEntity.ok("Education details saved successfully");
    }

    @GetMapping("/get/{email}")
    public Map<String, Object> getEducation(@PathVariable String email) {
        List<EducationDetailsEntity> list = educationDetailsService.getEducationByEmail(email);
        Map<String, Object> response = new HashMap<>();
        response.put("educationList", list);
        return response;
    }

    @GetMapping("/all")
    public List<EducationDetailsEntity> getAll() {
        return educationDetailsService.getAll();
    }
}