package com.mang.chatrestapi.controller;

import com.mang.chatrestapi.entity.User;
import com.mang.chatrestapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping(path = "{user}")
    public User getUser(@PathVariable(name = "user") String name) {
        return userService.getUser(name);
    }
}
