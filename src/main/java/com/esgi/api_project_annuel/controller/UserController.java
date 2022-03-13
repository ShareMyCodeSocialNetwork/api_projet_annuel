package com.esgi.api_project_annuel.controller;

import com.esgi.api_project_annuel.model.User;
import com.esgi.api_project_annuel.exceptions.UserNotFoundException;
import org.springframework.web.bind.annotation.*;
import com.esgi.api_project_annuel.repositories.UserRepository;

import java.util.List;

public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    List<User> all() {
        return userRepository.findAll();
    }

    @PostMapping("/users")
    User addUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/users/{id}")
    User findUser(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

}
