package com.mang.chatrestapi.service;

import com.mang.chatrestapi.entity.User;
import com.mang.chatrestapi.repository.UserRepository;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(String name) {
        User user = userRepository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "user " + name + " not found"));


        return user;
    }

    public User addUser(User user) {
        userRepository.findByName(user.getName())
                .ifPresent((e) -> {
                    throw new ResponseStatusException(HttpStatus.ACCEPTED,
                            "user " + user.getName() + " already exist");
                });

        return userRepository.save(user);
    }
}
