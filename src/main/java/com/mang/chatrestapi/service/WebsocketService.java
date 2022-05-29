package com.mang.chatrestapi.service;

import com.mang.chatrestapi.entity.Conversation;
import com.mang.chatrestapi.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Map;

@Service
public class WebsocketService {
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public WebsocketService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void notifyConversation(String receiver, Map<String, ? extends Serializable> conversation) {
        simpMessagingTemplate.convertAndSend("/conversation/"+receiver, conversation);
    }

    public void notifyMessage(String receiver, Map<String, ? extends Serializable> message) {
        simpMessagingTemplate.convertAndSend("/message/"+receiver, message);
    }
}
