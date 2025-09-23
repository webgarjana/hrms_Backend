package com.empsys.dao;

import com.empsys.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TicketRepository extends JpaRepository<TicketEntity, Long> {
    // ✅ Add this method to fetch tickets by email
    List<TicketEntity> findByEmail(String email);

	//TicketEntity raiseTicketWithNotification(TicketEntity ticket);
}
