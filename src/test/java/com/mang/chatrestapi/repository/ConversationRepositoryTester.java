package com.mang.chatrestapi.repository;

import com.mang.chatrestapi.entity.Conversation;
import com.mang.chatrestapi.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase
public class ConversationRepositoryTester {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Test
    void testFindByUserOneAndUserTwo() {

        User firman = userRepository.save(new User("firman"));
        User mang = userRepository.save(new User("mang"));
        User ramadhan = userRepository.save(new User("ramadhan"));

        conversationRepository.save(new Conversation(firman, mang));
        conversationRepository.save(new Conversation(firman, ramadhan));

        Assertions.assertEquals(
                Integer.valueOf(1),
                conversationRepository
                        .findByUser(firman)
                        .stream()
                        .findFirst()
                        .get()
                        .getId()
        );

        Assertions.assertEquals(
                Integer.valueOf(1),
                conversationRepository
                        .findByUser(mang)
                        .stream()
                        .findFirst()
                        .get()
                        .getId()
        );

        Assertions.assertEquals(
                Integer.valueOf(2),
                conversationRepository
                        .findByUser(firman)
                        .stream()
                        .filter(conversation -> (conversation.getUserTwo().getName() == "ramadhan"))
                        .findFirst()
                        .get()
                        .getId()
        );

        Assertions.assertEquals(
                Integer.valueOf(2),
                conversationRepository
                        .findByUser(ramadhan)
                        .stream()
                        .findFirst()
                        .get()
                        .getId()
        );
    }
}
