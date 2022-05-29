package com.mang.chatrestapi.response;

import com.mang.chatrestapi.entity.Conversation;
import com.mang.chatrestapi.entity.User;
import com.mang.chatrestapi.repository.ConversationRepository;
import com.mang.chatrestapi.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase
class ConversationResponseTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Test
    void generateResponse() {
        User firman = userRepository.save(new User("firman"));
        User mang = userRepository.save(new User("mang"));

        conversationRepository.save(new Conversation(firman, mang));
        Map<String, Object> response = ConversationResponse.generateResponse(firman.getId(),
                conversationRepository.findByUser(firman));

        System.out.println(response.toString());

    }
}