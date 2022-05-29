package com.mang.chatrestapi.controller;

import com.mang.chatrestapi.entity.Conversation;
import com.mang.chatrestapi.entity.User;
import com.mang.chatrestapi.repository.UserRepository;
import com.mang.chatrestapi.response.ConversationResponse;
import com.mang.chatrestapi.service.ConversationService;
import com.mang.chatrestapi.service.UserService;
import com.mang.chatrestapi.service.WebsocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(path = "/api/v1/users")
public class ConversationController {

    private ConversationService conversationService;

    private UserService userService;

    private WebsocketService websocketService;

    @Autowired
    public ConversationController(ConversationService conversationService,
                                  UserService userService,
                                  WebsocketService websocketService) {
        this.conversationService = conversationService;
        this.userService = userService;
        this.websocketService = websocketService;
    }

    @GetMapping(path = "{user}/conversations")
    Map<String, Object> getConversation(@PathVariable(name = "user") String username) {
        return conversationService.getConversation(username);
    }

    @PostMapping(path = "{user}/conversations")
    Map<String, ? extends Serializable> storeConversation(
            @PathVariable(name = "user") String sender,
            @RequestBody String receiver) {

        Map<String, Map> result = conversationService.storeConversation(sender, receiver);
        websocketService.notifyConversation(receiver, result.get("notify"));
        return result.get("response");
    }

}
