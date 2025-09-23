

package com.empsys.service;

import com.empsys.dao.ChatRepository;
import com.empsys.dao.NotificationRepository;
import com.empsys.dao.OnboardingRepository;
import com.empsys.entity.ChatEntity;
import com.empsys.entity.NotificationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private OnboardingRepository onboardingRepository;

    // Save new message and notify
    public ChatEntity saveMessage(ChatEntity message) {
        ChatEntity savedMessage = chatRepository.save(message);

        // Notification logic:
        if (message.getSender().equalsIgnoreCase("hr@gmail.com")) {
            if (message.getReceiver().equalsIgnoreCase("all")) {
                // HR to all employees
                notifyAllEmployees(message.getSender(), "New message from HR to all employees");
            } else {
                // HR to a specific employee
                saveNotification(message.getSender(), message.getReceiver(), "Message from HR");
            }
        } else {
            // Employee to HR
            saveNotification(message.getSender(), "hr@gmail.com", "New message from " + message.getSender());
        }

        return savedMessage;
    }

    // Get full chat history between HR and employee (both directions)
    public List<ChatEntity> getChatHistory(String sender, String receiver) {
        return chatRepository.findChatBetween(sender, receiver);
    }

    // Get all messages relevant to a specific employee
    public List<ChatEntity> getEmployeeMessages(String email) {
        return chatRepository.findMessagesForEmployee(email);
    }

    // Get all messages involving HR
    public List<ChatEntity> getAllMessagesForHR(String hrEmail) {
        return chatRepository.findAllMessagesForHR(hrEmail);
    }

    // 🔔 Save single notification with proper sender and receiver
    private void saveNotification(String senderEmail, String receiverEmail, String message) {
        NotificationEntity notification = new NotificationEntity();
        notification.setSenderEmail(senderEmail);
        notification.setReceiverEmail(receiverEmail);
        notification.setMessage(message);
        notification.setTimestamp(LocalDateTime.now());
        notification.setSeen(false);
        notificationRepository.save(notification);
    }

    // 🔔 Notify all onboarded employees
    private void notifyAllEmployees(String senderEmail, String message) {
        List<String> employeeEmails = onboardingRepository.findEmailsByRoleAndActive("employee", true); // You must implement this query

        for (String email : employeeEmails) {
            saveNotification(senderEmail, email, message);
        }
    }
}
