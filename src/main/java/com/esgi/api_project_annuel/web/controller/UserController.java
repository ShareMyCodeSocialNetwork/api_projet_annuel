package com.esgi.api_project_annuel.web.controller;

import com.esgi.api_project_annuel.Domain.entities.User;

import com.esgi.api_project_annuel.application.command.UserCommand;
import com.esgi.api_project_annuel.application.query.UserQuery;
import com.esgi.api_project_annuel.application.validation.UserValidationService;
import com.esgi.api_project_annuel.web.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.io.InvalidObjectException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping
public class UserController {

    @Autowired
    private final UserCommand userCommand;
    @Autowired
    private final UserQuery userQuery;

    private UserValidationService userValidationService;

    public UserController(UserCommand userCommand, UserQuery demandQuery){
        this.userCommand = userCommand;
        this.userQuery = demandQuery;
    }

    @PostMapping("/user/create")
    public  ResponseEntity<?> addUser(@RequestBody UserRequest userRequest) {

        User user = userCommand.create(userRequest);
        if(user != null)
        {
            return new ResponseEntity<User>(user, HttpStatus.CREATED);

        }else{

            return new ResponseEntity<String>("User not created",HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping(value = "/user", produces = { MimeTypeUtils.APPLICATION_JSON_VALUE }, headers = "Accept=application/json")
    public ResponseEntity<?> getUserAll(){
        Iterable<User> userAll = userQuery.getAll();
        try {
            return new ResponseEntity<Iterable<User>>(userAll, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("Error de recuperation des utilisateurs",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable int userId) {
        User user = userQuery.getById(userId);
        if (user != null && userId > 0) {
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }
        return new ResponseEntity<String>("L'id de cette utilisateur n'existe pas",HttpStatus.NOT_FOUND);
    }



    @PutMapping("/user/update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable int userId, @RequestBody User updatedUser) throws InvalidObjectException {
        User user = userCommand.update(userId, updatedUser);
        if (user != null) {
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }
        return new ResponseEntity<String>("Verifier le body ou l'entete envoyer",HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/user/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable int userId) {
        userCommand.delete(userId);
        return new ResponseEntity<>(
                "User " + userId + " deleted",
                HttpStatus.NO_CONTENT
        );
    }


}
