package com.empsys.controller;

import com.empsys.entity.ELoginEntity;
import com.empsys.service.ELoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class ELoginController {

    @Autowired
    private ELoginService eloginService;

    @PostMapping("/elogin")
    public String login(@RequestBody ELoginEntity elogin) {
        return eloginService.loginOrRegister(elogin.getUsername(), elogin.getPassword());
    }
}
