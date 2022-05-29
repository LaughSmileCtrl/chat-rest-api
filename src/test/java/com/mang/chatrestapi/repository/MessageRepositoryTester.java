package com.mang.chatrestapi.repository;

import com.mang.chatrestapi.entity.Conversation;
import com.mang.chatrestapi.entity.Message;
import com.mang.chatrestapi.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@DataJpaTest
@AutoConfigureTestDatabase
public class MessageRepositoryTester {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Test
    void testFindByConversationOrderByCreatedAtAsc() {

        User firman = userRepository.save(new User("firman"));
        User mang = userRepository.save(new User("mang"));

        Conversation conversation = conversationRepository.save(
                new Conversation(firman, mang));

        Assertions.assertEquals(
                Integer.valueOf(1),
                conversationRepository
                        .findByUser(firman)
                        .stream()
                        .findFirst()
                        .get()
                        .getId()
        );

        Date dateNow = new Date();
        Message message1 = new Message(
                conversation, firman, "Hay mang!");

        Message message2 = new Message(
                conversation, mang, "Halo firman, apa kabar?");



        dateNow = Date.from(
                ZonedDateTime.of(
                        LocalDateTime.of(2019, 2, 23, 4, 32),
                        ZoneId.of("Asia/Jakarta")
                ).toInstant());

//        message2.setCreatedAt(dateNow);
//        message2.setUpdatedAt(dateNow);

        messageRepository.save(message1);
        messageRepository.save(message2);

        Assertions.assertEquals("Hay mang!",
                messageRepository
                        .findByConversationOrderByCreatedAtDesc(conversation).stream().findFirst().get().getMessage());
    }
}
