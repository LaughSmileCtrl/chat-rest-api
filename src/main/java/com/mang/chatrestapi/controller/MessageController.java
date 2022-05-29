package com.mang.chatrestapi.controller;

import com.mang.chatrestapi.entity.Conversation;
import com.mang.chatrestapi.entity.Message;
import com.mang.chatrestapi.repository.ConversationRepository;
import com.mang.chatrestapi.service.MessageService;
import com.mang.chatrestapi.service.WebsocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(path = "/api/v1/users")
public class MessageController {
    private MessageService messageService;
    private WebsocketService websocketService;

    private ConversationRepository conversationRepository;

    @Autowired
    public MessageController(MessageService messageService,
                             WebsocketService websocketService,
                             ConversationRepository conversationRepository) {
        this.messageService = messageService;
        this.websocketService = websocketService;
        this.conversationRepository = conversationRepository;
    }

    @GetMapping(path = "{user}/conversations/{conversationId}/messages")
    Map<String, Object> getMessages(@PathVariable(name = "user") String username,
                                    @PathVariable(name = "conversationId") Long conversationId) {
        Set<Message> messageSet = messageService.getMessages(username, conversationId);
        return Map.of(
                "messages", messageSet,
                "count", messageSet.size()
        );
    }

    @PostMapping(path = "{user}/conversations/{conversationId}/messages")
    Message storeMessage(@PathVariable(name = "user") String username,
                         @PathVariable(name = "conversationId") Long conversationId,
                         @RequestBody String messageText) {
        Message message = messageService.storeMessage(username, conversationId, messageText);
        Conversation conversation = conversationRepository.findById(conversationId).get();
        String receiver = (conversation.getUserOne()
                                .getName().compareToIgnoreCase(username) == 0)
                ? conversation.getUserTwo().getName()
                : conversation.getUserOne().getName();

        Map<String, ? extends Serializable> response = Map.of(
                "createdAt", message.getCreatedAt(),
                "updatedAt", message.getUpdatedAt(),
                "id", message.getId(),
                "senderId", message.getSenderId(),
                "message", message.getMessage(),
                "conversationId", conversationId
        );

        websocketService.notifyMessage(receiver, response);
        return message;
    }

    @PutMapping(path = "{user}/conversations/{conversationId}/messages/{messageId}")
    Message updateMessage(@PathVariable(name = "user") String username,
                          @PathVariable(name = "conversationId") Long conversationId,
                          @PathVariable(name = "messageId") Long messageId,
                          @RequestBody String messageText) {
        return messageService.updateMessage(username, conversationId, messageId, messageText);
    }

    @DeleteMapping(path = "{user}/conversations/{conversationId}/messages/{messageId}")
    Map<String, String> updateMessage(@PathVariable(name = "user") String username,
                                      @PathVariable(name = "conversationId") Long conversationId,
                                      @PathVariable(name = "messageId") Long messageId) {
        return messageService.deleteMessage(username, conversationId, messageId);
    }
}
