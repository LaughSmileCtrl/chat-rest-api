package com.mang.chatrestapi.repository;


import com.mang.chatrestapi.entity.Conversation;
import com.mang.chatrestapi.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Set<Message> findByConversationOrderByCreatedAtDesc(Conversation conversation);

    Set<Message> findByConversationOrderByIdAsc(Conversation conversation);


}
