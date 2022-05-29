package com.mang.chatrestapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "conversations")
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_one_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User userOne;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_two_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User userTwo;

    @OneToMany(targetEntity = Message.class,
            mappedBy = "conversation", cascade = CascadeType.ALL)
    private Set<Message> messages;

    public Conversation() {
    }

    public Conversation(User userOne, User userTwo) {
        this.userOne = userOne;
        this.userTwo = userTwo;
    }

    public Long getId() {
        return Id;
    }

    public User getUserOne() {
        return userOne;
    }

    public User getUserTwo() {
        return userTwo;
    }

    public void addMessage(Message message) {
        message.setConversation(this);
        messages.add(message);
    }
}
