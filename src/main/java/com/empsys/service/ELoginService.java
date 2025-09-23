package com.empsys.service;

import com.empsys.entity.ELoginEntity;
import com.empsys.dao.ELoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ELoginService {

    @Autowired
    private ELoginRepository eloginRepository;

    public String loginOrRegister(String username, String password) {
        Optional<ELoginEntity> existingUser = eloginRepository.findByUsername(username);

        if (existingUser.isPresent()) {
            if (existingUser.get().getPassword().equals(password)) {
                return "Login successful!";
            } else {
                return "Invalid password!";
            }
        } else {
            ELoginEntity newUser = new ELoginEntity(username, password);
            eloginRepository.save(newUser);
            return "User registered successfully!";
        }
    }
}
