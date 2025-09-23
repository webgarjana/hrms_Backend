package com.empsys.controller;

import com.empsys.entity.HLoginEntity;
import com.empsys.service.HLoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class HLoginController {

    @Autowired
    private HLoginService hloginService;

    @PostMapping("/hlogin")
    public String login(@RequestBody HLoginEntity hlogin) {
        return hloginService.loginOrRegister(hlogin.getUsername(), hlogin.getPassword());
    }
}
