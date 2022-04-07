package com.esgi.api_project_annuel.web.controller;

import com.esgi.api_project_annuel.Domain.entities.Post;
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
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private final UserCommand userCommand;
    @Autowired
    private final UserQuery userQuery;

    private final UserValidationService userValidationService = new UserValidationService();

    public UserController(UserCommand userCommand, UserQuery userQuery){
        this.userCommand = userCommand;
        this.userQuery = userQuery;
    }

    @PostMapping("/create")
    public  ResponseEntity<?> addUser(@RequestBody UserRequest userRequest) {
        var user = userCommand.create(userRequest);
        if(userValidationService.isUserValid(user))
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        else
            return new ResponseEntity<>("User not created",HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/", produces = { MimeTypeUtils.APPLICATION_JSON_VALUE }, headers = "Accept=application/json")
    public ResponseEntity<?> getUserAll(){
        Iterable<User> userAll = userQuery.getAll();
        try {
            return new ResponseEntity<>(userAll, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error de recuperation des utilisateurs",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable int userId) {
        var user = userQuery.getById(userId);
        if (user != null && userId > 0)
            return new ResponseEntity<>(user, HttpStatus.OK);
        return new ResponseEntity<>("L'id de cette utilisateur n'existe pas",HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable int userId) {
        userCommand.delete(userId);
        return new ResponseEntity<>(
                "User " + userId + " deleted",
                HttpStatus.NO_CONTENT
        );
    }

    //todo : to test
    @GetMapping("/user_posts/{userId}")
    public ResponseEntity<?> getPostByUserId(@PathVariable int userId){
        var user = userQuery.getById(userId);
        if(user == null)
            return new ResponseEntity<>("User not exist", HttpStatus.BAD_REQUEST);
        else{
            List<Post> userPosts = userQuery.getPosts(user);
            return new ResponseEntity<>(userPosts, HttpStatus.OK);
        }
    }

    @PatchMapping("/password/{userId}")
    public ResponseEntity<?> changePassword(@PathVariable int userId, @RequestBody UserRequest userRequest){
        var user = userQuery.getById(userId);
        if(user == null)
            return new ResponseEntity<>("User not exist", HttpStatus.BAD_REQUEST);
        else{
            var userNewPassword = userCommand.changePassword(userId, userRequest.password);
            if(userNewPassword == null)
                return new ResponseEntity<>("Invalid User", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(userNewPassword, HttpStatus.OK);
        }
    }

    /*
     * todo :
     *      - change email
     *      - change name (last & first)
     *      - connexion ?
     */
}
