package com.empsys.controller;

import com.empsys.entity.ChatEntity;
import com.empsys.entity.OnboardingEntity;
import com.empsys.service.ChatService;
import com.empsys.dao.OnboardingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin("http://localhost:3000") // Consider restricting to trusted origins in production
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private OnboardingRepository onboardingRepository;

    // ✅ Send a message (personal or broadcast)
    @PostMapping("/send")
    public ChatEntity sendMessage(@RequestBody ChatEntity message) {
        return chatService.saveMessage(message);
    }

    // ✅ Get chat history between 2 users (sender ↔ receiver)
    @GetMapping("/history/{sender}/{receiver}")
    public List<ChatEntity> getChatHistory(@PathVariable String sender, @PathVariable String receiver) {
        return chatService.getChatHistory(sender, receiver);
    }

    // ✅ Employee inbox: get both personal and broadcast messages
    @GetMapping("/employee/{email}")
    public List<ChatEntity> getEmployeeMessages(@PathVariable String email) {
        return chatService.getEmployeeMessages(email);
    }

    // ✅ HR inbox: get all employee messages (including broadcast and personal)
    @GetMapping("/hr/all")
    public List<ChatEntity> getAllMessagesForHR() {
        return chatService.getAllMessagesForHR("hr@company.com"); // hardcoded HR email
    }

//    // ✅ Get all onboarded employees
//    @GetMapping("/employee/onboarded")
//    public List<OnboardingEntity> getOnboardedEmployees() {
//        return onboardingRepository.findByActiveTrue(); // fetch only active=true employees
//    }
    
 // ✅ In your HRChatController.java or EmployeeController.java
    @GetMapping("/employee/onboarded/emails")
    public List<String> getOnboardedEmployeeEmails() {
        return onboardingRepository.findByActiveTrueAndRole("employee")
                                   .stream()
                                   .map(OnboardingEntity::getEmail)
                                   .collect(Collectors.toList());
    }


}


