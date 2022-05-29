package com.mang.chatrestapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "conversation_messages")
public class Message extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    @JsonBackReference
    private Conversation conversation;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    @JsonBackReference
    private User sender;

    @Transient
    @JsonSerialize
    private Long senderId;

    @Column(columnDefinition = "TEXT")
    private String message;


    public Message() {
    }

    public Message(User sender, String message) {
        this.sender = sender;
        this.senderId = this.sender.getId();
        this.message = message;
    }

    public Message(Conversation conversation,
                   User sender,
                   String message) {
        this.conversation = conversation;
        this.sender = sender;
        this.senderId = this.sender.getId();
        this.message = message;
    }

    public Long getId() {
        return id;
    }


    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
        this.senderId = this.sender.getId();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getSenderId() {
        return sender.getId();
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }
}