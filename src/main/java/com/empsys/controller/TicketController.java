



package com.empsys.controller;

import com.empsys.entity.TicketEntity;
import com.empsys.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    // ✅ Employee raises a new ticket → HR receives notification
    @PostMapping("/raise")
    public TicketEntity raiseTicket(@RequestBody TicketEntity ticket) {
        return ticketService.raiseTicket(ticket);
    }

    // ✅ HR replies to a ticket → Employee receives notification
    @PostMapping("/hr/tickets/{id}/reply")
    public TicketEntity replyToTicket(
            @PathVariable Long id,
            @RequestBody TicketEntity ticketRequest // contains hrReply and status
    ) {
        return ticketService.updateTicketReply(id, ticketRequest.getHrReply(), ticketRequest.getStatus());
    }

    // ✅ HR fetches all tickets
    @GetMapping("/hr/tickets")
    public List<TicketEntity> getAllTickets() {
        return ticketService.getAllTickets();
    }

    // ✅ Employee views only their tickets
    @GetMapping("/tickets")
    public List<TicketEntity> getTicketsByEmail(@RequestParam String email) {
        return ticketService.getTicketsByEmail(email);
    }

    // ✅ HR updates only ticket status
    @PutMapping("/hr/tickets/{id}/status")
    public TicketEntity updateTicketStatus(
            @PathVariable Long id,
            @RequestBody TicketEntity ticketRequest
    ) {
        return ticketService.updateTicketReply(id, null, ticketRequest.getStatus()); // hrReply is null
    }
}

