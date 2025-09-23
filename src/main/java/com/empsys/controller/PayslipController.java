package com.empsys.controller;

import com.empsys.entity.PayslipEntity;
import com.empsys.service.PayslipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/payroll")
@CrossOrigin(origins = "http://localhost:3000") // React frontend URL
public class PayslipController {

    @Autowired
    private PayslipService payslipService;

    // POST endpoint to generate and store payslip + PDF
    @PostMapping("/payslip")
    public PayslipEntity generatePayslip(@RequestBody PayslipEntity payslip) {
        return payslipService.generatePayslip(payslip);
    }

    // GET endpoint to fetch payslips by employee email
    @GetMapping("/payslip/{email}")
    public List<PayslipEntity> getPayslips(@PathVariable String email) {
        return payslipService.getPayslipsByEmail(email);
    }

    // GET endpoint to download generated PDF
    @GetMapping("/payslip/pdf/{filename}")
    public ResponseEntity<Resource> downloadPayslipPdf(@PathVariable String filename) throws IOException {
        File file = new File("uploads/" + filename);
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(file.length())
                .body(resource);
    }
    
 // GET endpoint for HR to view all employee payslips
    @GetMapping("/payslip/all")
    public List<PayslipEntity> getAllPayslips() {
        return payslipService.getAllPayslips();
    }

}
