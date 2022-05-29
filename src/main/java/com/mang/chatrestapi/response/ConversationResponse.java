package com.mang.chatrestapi.response;

import com.mang.chatrestapi.entity.Conversation;
import com.mang.chatrestapi.entity.User;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConversationResponse {
    public static Map<String, Object> generateResponse(Long userId, Set<Conversation> conversations) {
        Set<Map<String, ? extends Serializable>> conversationSet = conversations.stream()
                .flatMap(conversation -> {
                    User receiver = conversation.getUserTwo();

                    if (conversation.getUserTwo().getId() == userId) {
                        receiver = conversation.getUserOne();
                    }

                    return Stream.of(Map.of(
                            "id", conversation.getId(),
                            "receiverId", receiver.getId(),
                            "receiverName", receiver.getName()
                    ));
                })
                .collect(Collectors.toSet());

        return  Map.of(
                "conversations", conversationSet,
                "count", conversationSet.size()
        );
    }
}
