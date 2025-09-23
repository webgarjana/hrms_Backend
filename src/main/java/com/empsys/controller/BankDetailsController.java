package com.empsys.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.empsys.entity.BankDetailsEntity;
import com.empsys.service.BankDetailsService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000") // Allow React access
@RestController
@RequestMapping("/api/payroll")
public class BankDetailsController {

    @Autowired
    private BankDetailsService service;

    @PostMapping("/bankdetails")
    public BankDetailsEntity saveBankDetails(@RequestBody BankDetailsEntity details) {
        return service.saveBankDetails(details);
    }

    @GetMapping("/bankdetails")
    public List<BankDetailsEntity> getAllBankDetails() {
        return service.getAllBankDetails();
    }
}

