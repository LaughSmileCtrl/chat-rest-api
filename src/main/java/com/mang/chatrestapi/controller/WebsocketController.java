package com.mang.chatrestapi.controller;

import com.mang.chatrestapi.entity.Conversation;
import com.mang.chatrestapi.entity.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.io.Serializable;
import java.util.Map;

@Controller
public class WebsocketController {

    @SendTo("/conversation/{username}")
    Map<String, ? extends Serializable> sendConversation(Map<String, ? extends Serializable> conversation) {
        return conversation;
    }

    @SendTo("/message/{username}")
    Message sendMessage(Message message) {
        return message;
    }
}
