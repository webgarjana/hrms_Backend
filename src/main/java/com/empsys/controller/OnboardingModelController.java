package com.empsys.controller;





import com.empsys.service.OnboardingModelServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;



@RestController
@RequestMapping("/api/hr")
@CrossOrigin(origins = "*")
public class OnboardingModelController {

    @Autowired
    private OnboardingModelServices onboardingModelService;

    @PostMapping("/revoke-access")
    public String revokeEmployeeAccess(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String employeeId = payload.get("employeeId");

        // ✅ Revoke access
        String result = onboardingModelService.revokeAccessByEmailOrId(email, employeeId);

        // ✅ Delete from database if revoke was successful
        if(result.contains("revoked")) {
            onboardingModelService.deleteEmployeeByEmailOrId(email, employeeId);
        }

        return result;
    }

}