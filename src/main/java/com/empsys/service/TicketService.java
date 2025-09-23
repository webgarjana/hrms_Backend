



package com.empsys.service;

import com.empsys.dao.NotificationRepository;
import com.empsys.dao.TicketRepository;
import com.empsys.entity.NotificationEntity;
import com.empsys.entity.TicketEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    // ✅ Employee raises ticket and HR gets notification
    public TicketEntity raiseTicket(TicketEntity ticket) {
        ticket.setStatus("Pending"); // default status
        TicketEntity savedTicket = ticketRepository.save(ticket);

        // Create notification for HR
        NotificationEntity notification = new NotificationEntity();
        notification.setSenderEmail(ticket.getEmail());
        notification.setReceiverEmail("hr@gmail.com");
        notification.setMessage("New ticket raised by " + ticket.getEmpId() + " (" + ticket.getEmail() + ")");
        notification.setSeen(false);
        notification.setTimestamp(LocalDateTime.now());

        notificationRepository.save(notification);

        return savedTicket;
    }

    // ✅ Get all tickets for HR
    public List<TicketEntity> getAllTickets() {
        return ticketRepository.findAll();
    }

    // ✅ Get tickets by employee email (for employee view)
    public List<TicketEntity> getTicketsByEmail(String email) {
        return ticketRepository.findByEmail(email);
    }

    // ✅ HR replies to ticket → Employee gets notification
    public TicketEntity updateTicketReply(Long id, String hrReply, String status) {
        Optional<TicketEntity> optionalTicket = ticketRepository.findById(id);
        if (!optionalTicket.isPresent()) {
            throw new RuntimeException("Ticket not found with ID: " + id);
        }

        TicketEntity ticket = optionalTicket.get();
        ticket.setHrReply(hrReply);
        ticket.setStatus(status);
        TicketEntity updatedTicket = ticketRepository.save(ticket);

        // Create notification for employee
        if (ticket.getEmail() != null && !ticket.getEmail().isEmpty()) {
            NotificationEntity notification = new NotificationEntity();
            notification.setSenderEmail("hr@gmail.com");
            notification.setReceiverEmail(ticket.getEmail());
            notification.setMessage("HR replied to your ticket: " + hrReply + " | Status: " + status);
            notification.setSeen(false);
            notification.setTimestamp(LocalDateTime.now());

            notificationRepository.save(notification);
        }

        return updatedTicket;
    }
}

