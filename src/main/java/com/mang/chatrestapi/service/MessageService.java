package com.mang.chatrestapi.service;

import com.mang.chatrestapi.entity.Conversation;
import com.mang.chatrestapi.entity.Message;
import com.mang.chatrestapi.entity.User;
import com.mang.chatrestapi.repository.ConversationRepository;
import com.mang.chatrestapi.repository.MessageRepository;
import com.mang.chatrestapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class MessageService {

    private UserRepository userRepository;
    private ConversationRepository conversationRepository;
    private MessageRepository messageRepository;

    @Autowired
    public MessageService(UserRepository userRepository,
                          ConversationRepository conversationRepository,
                          MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
    }

    private Conversation getConversation(User sender, Long conversationId) {
        return  conversationRepository.findByUser(sender).stream()
                .filter(conversation -> (conversation.getId() == conversationId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "conversation " + conversationId + " not found"));
    }

    private User getSender(String username) {
        return userRepository.findByName(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "user " + username + " not found"));
    }

    public Message storeMessage(String username,
                                Long conversationId,
                                String messageText) {
        User sender = getSender(username);
        Conversation conversation = getConversation(sender, conversationId);
        Message message = new Message(conversation, sender, messageText);

        return messageRepository.save(message);
    }

    public Set<Message> getMessages(String username, Long conversationId) {
        User sender = getSender(username);
        Conversation conversation = getConversation(sender, conversationId);

        return messageRepository.findByConversationOrderByIdAsc(conversation);
    }

    @Transactional
    public Message updateMessage(String username, Long conversationId,
                                 Long messageId, String messageText) {
        User sender = getSender(username);
        getConversation(sender, conversationId);
        Message message = messageRepository.findById(messageId)
                .filter(msg -> (msg.getSender()
                        .getName().compareToIgnoreCase(sender.getName()) == 0))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "message " + messageId + " not found"));

        message.setMessage(messageText);
        message.setUpdatedAt(new Date());
        return message;
    }

    @Transactional
    public Map<String, String> deleteMessage(String username, Long conversationId,
                                             Long messageId) {
        User sender = getSender(username);
        getConversation(sender, conversationId);
        messageRepository.findById(messageId)
                .filter(msg -> (msg.getSender()
                        .getName().compareToIgnoreCase(sender.getName()) == 0))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "message " + messageId + " not found"));
        messageRepository.deleteById(messageId);
        return Map.of("status", "success");
    }
}
