package com.mang.chatrestapi.repository;

import com.mang.chatrestapi.entity.Conversation;
import com.mang.chatrestapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    @Query("SELECT c FROM Conversation c " +
            "WHERE c.userOne = ?1 OR c.userTwo = ?1 ")
    Set<Conversation> findByUser(User userOne);
}
