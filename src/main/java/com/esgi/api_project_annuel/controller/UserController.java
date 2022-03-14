package com.esgi.api_project_annuel.controller;

import com.esgi.api_project_annuel.model.User;
import com.esgi.api_project_annuel.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InvalidObjectException;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping({"/{userId}"})
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user) throws InvalidObjectException {
        User userCreated = userService.createUser(user);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("user", "/user/" + userCreated.getId());
        return new ResponseEntity<>(userCreated, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping({"/{userId}"})
    public ResponseEntity<User> updateUser(@PathVariable("userId") Long userId, @RequestBody User user) throws InvalidObjectException {
        userService.updateUser(userId, user);
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @DeleteMapping({"/{userId}"})
    public ResponseEntity<User> deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
