package com.empsys.dao;

import com.empsys.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatEntity, Long> {

    // 🔁 Get 1-to-1 chat between HR and selected employee
    @Query("SELECT c FROM ChatEntity c WHERE " +
           "(c.sender = :sender AND c.receiver = :receiver) " +
           "OR (c.sender = :receiver AND c.receiver = :sender) " +
           "ORDER BY c.id ASC")
    List<ChatEntity> findChatBetween(@Param("sender") String sender, @Param("receiver") String receiver);

    // 📥 For employee view – includes HR messages, employee messages, and broadcasts
    @Query("SELECT c FROM ChatEntity c WHERE " +
           "c.receiver = :email OR c.receiver = 'all' OR c.sender = :email " +
           "ORDER BY c.id ASC")
    List<ChatEntity> findMessagesForEmployee(@Param("email") String email);
    
 // ✅ NEW: HR sees their own messages, employee replies, and broadcasts
    @Query("SELECT c FROM ChatEntity c WHERE " +
           "c.sender = :hrEmail OR c.receiver = :hrEmail OR c.receiver = 'all' " +
           "ORDER BY c.id ASC")
    List<ChatEntity> findAllMessagesForHR(@Param("hrEmail") String hrEmail);

}
