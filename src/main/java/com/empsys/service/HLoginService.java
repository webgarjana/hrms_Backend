package com.empsys.service;

import com.empsys.entity.HLoginEntity;
import com.empsys.dao.HLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HLoginService {

    @Autowired
    private HLoginRepository hloginRepository;

    public String loginOrRegister(String username, String password) {
        Optional<HLoginEntity> existingUser = hloginRepository.findByUsername(username);

        if (existingUser.isPresent()) {
            if (existingUser.get().getPassword().equals(password)) {
                return "Login successful!";
            } else {
                return "Invalid password!";
            }
        } else {
            HLoginEntity newUser = new HLoginEntity(username, password);
            hloginRepository.save(newUser);
            return "User registered successfully!";
        }
    }
}
