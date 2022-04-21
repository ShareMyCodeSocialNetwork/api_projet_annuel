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
import java.util.Objects;

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
        String password = userRequest.password;
        String email = userRequest.email;
        String firstname = userRequest.firstname;
        String lastname = userRequest.lastname;
        String profilePicture = userRequest.profilePicture;

        if(password == null || email == null || profilePicture == null || lastname == null || firstname == null ||
        password.equals("") || email.equals("") || profilePicture.equals("") || lastname.equals("") || firstname.equals(""))
            return new ResponseEntity<>("Missing properties", HttpStatus.BAD_REQUEST);

        if(userQuery.userEmailExist(userRequest.email))
            return new ResponseEntity<>("Email already taken", HttpStatus.BAD_REQUEST);

        var user = new User();
        user.setFirstName(firstname);
        user.setPassword(password);
        user.setProfilePicture(profilePicture);
        user.setLastName(lastname);
        user.setEmail(email);

        if(!userValidationService.isUserValid(user))
            return new ResponseEntity<>("User not created, invalid properties",HttpStatus.BAD_REQUEST);

        var createdUser = userCommand.create(userRequest);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }



    @GetMapping(value = "/", produces = { MimeTypeUtils.APPLICATION_JSON_VALUE }, headers = "Accept=application/json")
    public ResponseEntity<?> getUserAll(){
        Iterable<User> userAll = userQuery.getAll();
        try {
            return new ResponseEntity<>(userAll, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("user recovery error",HttpStatus.BAD_REQUEST);
        }
    }



    @GetMapping(value = "/{userId}", produces = { MimeTypeUtils.APPLICATION_JSON_VALUE }, headers = "Accept=application/json")
    public ResponseEntity<?> getUserById(@PathVariable int userId) {
        var user = userQuery.getById(userId);
        if (user != null && userId > 0)
            return new ResponseEntity<>(user, HttpStatus.OK);
        return new ResponseEntity<>("Id not found",HttpStatus.NOT_FOUND);
    }




    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest userRequest){
        if(userRequest.email == null || userRequest.password == null)
            return new ResponseEntity<>("Invalid properties", HttpStatus.BAD_REQUEST);
        var user = userQuery.getByEmailAndPassword(userRequest.email, userRequest.password);
        if(user == null)
            return new ResponseEntity<>("Invalid email or password", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }




    //todo : to test
    @GetMapping("/posts/{userId}")
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
        String password = userRequest.password;

        if(password == null || password.equals(""))
            return new ResponseEntity<>("Missing properties", HttpStatus.BAD_REQUEST);

        var user = userQuery.getById(userId);
        if(user == null)
            return new ResponseEntity<>("User not exist", HttpStatus.BAD_REQUEST);

        var userNewPassword = userCommand.changePassword(userId, password);
        if(userNewPassword == null)
            return new ResponseEntity<>("Invalid User", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(userNewPassword, HttpStatus.OK);
    }




    @PatchMapping("/email/{userId}")
    public ResponseEntity<?> changeEmail(@PathVariable int userId, @RequestBody UserRequest userRequest){

        String email = userRequest.email;
        if(Objects.equals(email, "") || email == null)
            return new ResponseEntity<>("Missing properties", HttpStatus.BAD_REQUEST);

        var user = userQuery.getById(userId);
        if(user == null)
            return new ResponseEntity<>("User not exist", HttpStatus.BAD_REQUEST);

        if(userQuery.userEmailExist(email))
            return new ResponseEntity<>("Email already taken", HttpStatus.BAD_REQUEST);

        user.setEmail(email);
        if(!userValidationService.isUserValid(user))
            return new ResponseEntity<>("Invalid user's properties", HttpStatus.BAD_REQUEST);

        var changedUserEmail = userCommand.changeEmail(userId, userRequest);
        return new ResponseEntity<>(changedUserEmail, HttpStatus.OK);

    }




    @PatchMapping("/lastname/{userId}")
    public ResponseEntity<?> changeLastname(@PathVariable int userId, @RequestBody UserRequest userRequest){
        String lastname = userRequest.lastname;

        if(Objects.equals(lastname, "") || lastname == null)
            return new ResponseEntity<>("Missing properties", HttpStatus.BAD_REQUEST);

        var user = userQuery.getById(userId);
        if(user == null)
            return new ResponseEntity<>("User not exist", HttpStatus.BAD_REQUEST);

        user.setLastName(lastname);
        if(!userValidationService.isUserValid(user))
            return new ResponseEntity<>("Invalid user's properties", HttpStatus.BAD_REQUEST);

        var changedLastnameUser = userCommand.changeLastname(userId,userRequest);
        return new ResponseEntity<>(changedLastnameUser, HttpStatus.OK);
    }




    @PatchMapping("/firstname/{userId}")
    public ResponseEntity<?> changeFirstname(@PathVariable int userId, @RequestBody UserRequest userRequest){
        String firstname = userRequest.firstname;

        if(Objects.equals(firstname, "") || firstname == null)
            return new ResponseEntity<>("Missing properties", HttpStatus.BAD_REQUEST);

        var user = userQuery.getById(userId);
        if(user == null)
            return new ResponseEntity<>("User not exist", HttpStatus.BAD_REQUEST);

        user.setFirstName(firstname);
        if(!userValidationService.isUserValid(user))
            return new ResponseEntity<>("Invalid user's properties", HttpStatus.BAD_REQUEST);

        var changedFirstnameUser = userCommand.changeFirstname(userId,userRequest);
        return new ResponseEntity<>(changedFirstnameUser, HttpStatus.OK);
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable int userId) {
        var user = userQuery.getById(userId);
        if(user == null)
            return new ResponseEntity<>(
                    " deleted",
                    HttpStatus.BAD_REQUEST
            );

        userCommand.delete(userId);
        return new ResponseEntity<>(
                "User " + userId + " deleted",
                HttpStatus.ACCEPTED
        );

    }
}
