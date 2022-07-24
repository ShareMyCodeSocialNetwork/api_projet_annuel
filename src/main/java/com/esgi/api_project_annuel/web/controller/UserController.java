package com.esgi.api_project_annuel.web.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.esgi.api_project_annuel.Domain.entities.User;
import com.esgi.api_project_annuel.Domain.repository.UserRepository;
import com.esgi.api_project_annuel.application.command.UserCommand;
import com.esgi.api_project_annuel.application.query.UserQuery;
import com.esgi.api_project_annuel.web.request.UserRequest;
import com.esgi.api_project_annuel.web.response.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private final UserCommand userCommand;
    @Autowired
    private final UserQuery userQuery;

    public UserController(UserCommand userCommand, UserQuery userQuery){
        this.userCommand = userCommand;
        this.userQuery = userQuery;
    }

    @PostMapping("/create")
    public  ResponseEntity<UserResponse> addUser(@RequestBody UserRequest userRequest) {
        if(userQuery.userEmailExist(userRequest.email) || userQuery.userPseudoExist(userRequest.pseudo))
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        var createdUser = userCommand.create(userRequest);
        if(createdUser == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(userToUserResponse(createdUser), HttpStatus.CREATED);
    }


    @GetMapping("/pseudo/{pseudo}")
    public ResponseEntity<UserResponse> getByPseudo(@PathVariable String pseudo){
        var user = userQuery.getByPseudo(pseudo);
        if(user == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(userToUserResponse(user),
                HttpStatus.OK
        );
    }


    @GetMapping("/search/{value}")
    public ResponseEntity<List<UserResponse>> searchUser(@PathVariable String value){
        var users = new ArrayList<User>();

        var byPseudo = userQuery.getByPseudo(value);
        var byEmail = userQuery.getByEmail(value);
        var byFirstname = userQuery.getAllByFirstname(value);
        var byLastname = userQuery.getAllByLastname(value);

        if (byEmail != null)
            users.add(byEmail);
        if (byPseudo != null)
            users.add(byPseudo);
        if (byFirstname.size() > 0)
            users.addAll(byFirstname);
        if (byLastname.size() > 0)
            users.addAll(byLastname);

        HashSet<User> uniqueUsers = new HashSet<>(users);
        return new ResponseEntity<>(
                hashSetUserToListUserResponse(uniqueUsers),
                HttpStatus.OK
        );
    }


    @GetMapping("/search/levenshtein/{value}")
    public ResponseEntity<List<UserResponse>> searchUserLevenshtein(@PathVariable String value){
        var users = new ArrayList<User>();

        var byPseudo = userQuery.getByPseudoLevenshtein(value);
        var byEmail = userQuery.getByEmailLevenshtein(value);
        var byFirstname = userQuery.getAllByFirstnameLevenshtein(value);
        var byLastname = userQuery.getAllByLastnameLevenshtein(value);

        if (byEmail.size() > 0)
            users.addAll(byEmail);
        if (byPseudo.size() > 0)
            users.addAll(byPseudo);
        if (byFirstname.size() > 0)
            users.addAll(byFirstname);
        if (byLastname.size() > 0)
            users.addAll(byLastname);

        HashSet<User> uniqueUsers = new HashSet<>(users);
        return new ResponseEntity<>(
                hashSetUserToListUserResponse(uniqueUsers),
                HttpStatus.OK
        );
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse> getByEmail(@PathVariable String email){
        var user = userQuery.getByEmail(email);
        if(user == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(userToUserResponse(user),
                HttpStatus.OK
        );
    }


    @GetMapping(value = "/", produces = { MimeTypeUtils.APPLICATION_JSON_VALUE }, headers = "Accept=application/json")
    public ResponseEntity<List<UserResponse>> getUserAll(){
        return new ResponseEntity<>(
                listUserToListUserResponse(userQuery.getAll()),
                HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}", produces = { MimeTypeUtils.APPLICATION_JSON_VALUE }, headers = "Accept=application/json")
    public ResponseEntity<UserResponse> getUserById(@PathVariable int userId) {
        var user = userQuery.getById(userId);
        if(user == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(userToUserResponse(
                user),
                HttpStatus.OK
        );
    }


    @PatchMapping("/update/password/{userId}")
    public ResponseEntity<UserResponse> changePassword(@PathVariable int userId, @RequestBody UserRequest userRequest){
        var userNewPassword = userCommand.changePassword(userId, userRequest);
        if(userNewPassword == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(userToUserResponse(userNewPassword), HttpStatus.OK);
    }

    @PatchMapping("/update/email/{userId}")
    public ResponseEntity<UserResponse> changeEmail(@PathVariable int userId, @RequestBody UserRequest userRequest){
        var user = userCommand.changeEmail(userId, userRequest);
        if(user == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(userToUserResponse(user), HttpStatus.OK);

    }

    @PatchMapping("/update/pseudo/{userId}")
    public ResponseEntity<UserResponse> changePseudo(@PathVariable int userId, @RequestBody UserRequest userRequest){
        var user = userCommand.changePseudo(userId, userRequest);
        if(user == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(userToUserResponse(user), HttpStatus.OK);

    }




    @PatchMapping("/update/lastname/{userId}")
    public ResponseEntity<UserResponse> changeLastname(@PathVariable int userId, @RequestBody UserRequest userRequest){
        var user = userCommand.changeLastname(userId, userRequest);
        if(user == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(userToUserResponse(user), HttpStatus.OK);
    }




    @PatchMapping("/update/firstname/{userId}")
    public ResponseEntity<UserResponse> changeFirstname(@PathVariable int userId, @RequestBody UserRequest userRequest){
        var user = userCommand.changeFirstname(userId, userRequest);
        if(user == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(userToUserResponse(user), HttpStatus.OK);
    }


    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable int userId) {
        var user = userQuery.getById(userId);
        if(user == null)
            return new ResponseEntity<>(
                    "User " + userId + " not exist",
                    HttpStatus.BAD_REQUEST
            );
        userCommand.delete(userId);
        return new ResponseEntity<>(
                "User " + userId + " deleted",
                HttpStatus.ACCEPTED
        );

    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userRepository.findByEmail(username);
                String access_token = JWT.create()
                        .withSubject(user.getFirstname())
                        .withExpiresAt(new java.sql.Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().getTitlePermission())
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token",access_token);
                tokens.put("refresh_token",refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            }catch (Exception exception){
                response.setHeader("error",exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> errors = new HashMap<>();
                errors.put("error_message",exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), errors);
            }
        }else{
            throw new RuntimeException("Refresh token is missing");
        }
    }




    private UserResponse userToUserResponse(User user){
        return new UserResponse()
                .setId(user.getId())
                .setFirstname(user.getFirstname())
                .setEmail(user.getEmail())
                .setLastname(user.getLastname())
                .setPseudo(user.getPseudo())
                .setPassword(user.getPassword())
                .setProfilePicture(user.getProfilePicture());
    }

    private List<UserResponse> listUserToListUserResponse(List<User> users){
        List<UserResponse> userResponses = new ArrayList<>();
        users.forEach(user -> userResponses.add(userToUserResponse(user)));
        return userResponses;
    }
    private List<UserResponse> hashSetUserToListUserResponse(HashSet<User> users){
        List<UserResponse> userResponses = new ArrayList<>();
        users.forEach(user -> userResponses.add(userToUserResponse(user)));
        return userResponses;
    }
}
