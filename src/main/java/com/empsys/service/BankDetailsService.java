package com.empsys.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empsys.dao.BankDetailsRepository;
import com.empsys.entity.BankDetailsEntity;

import java.util.List;

@Service
public class BankDetailsService {

    @Autowired
    private BankDetailsRepository repository;

    public BankDetailsEntity saveBankDetails(BankDetailsEntity details) {
        return repository.save(details);
    }

    public List<BankDetailsEntity> getAllBankDetails() {
        return repository.findAll();
    }
}

