package com.mang.chatrestapi.service;

import com.mang.chatrestapi.entity.Conversation;
import com.mang.chatrestapi.entity.User;
import com.mang.chatrestapi.repository.ConversationRepository;
import com.mang.chatrestapi.repository.UserRepository;
import com.mang.chatrestapi.response.ConversationResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class ConversationService {
    private ConversationRepository conversationRepository;
    private UserRepository userRepository;

    public ConversationService(ConversationRepository conversationRepository,
                               UserRepository userRepository) {
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
    }

    public Map<String, Object> getConversation(String username) {
        User user = userRepository.findByName(username)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "user " + username + " not exist"));
        Set<Conversation> userConversation = conversationRepository.findByUser(user);

        return ConversationResponse.generateResponse(user.getId(), userConversation);

    }

    public Map<String, Map> storeConversation(String senderName,
                                                                 String receiverName) {
        User sender = userRepository.findByName(senderName)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "user " + senderName + " not exist"));

        User receiver = userRepository.findByName(receiverName)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.ACCEPTED,
                                "receiver " + receiverName + " not exist"));

        Optional<Conversation> conversationResult = conversationRepository.findByUser(sender)
                .stream()
                .filter(conversation -> (
                        conversation.getUserOne().getName()
                                .compareToIgnoreCase(receiverName) == 0
                        || conversation.getUserTwo().getName()
                                .compareToIgnoreCase(receiverName) == 0))
                .findFirst();

        if (! conversationResult.isPresent()) {
            conversationResult = Optional.of(
                    conversationRepository.save(new Conversation(sender, receiver)));
        }

        Map<String, ? extends Serializable> response = Map.of(
                "receiverName", receiver.getName(),
                "receiverId", receiver.getId(),
                "id", conversationResult.get().getId()
        );

        Map<String, ? extends Serializable> notify = Map.of(
                "senderName", sender.getName(),
                "senderId", sender.getId(),
                "id", conversationResult.get().getId()
        );

        return Map.of(
                "response", response,
                "notify", notify
        );
    }
}
