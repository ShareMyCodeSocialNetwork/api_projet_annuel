package com.esgi.api_project_annuel.web.controller;

import com.esgi.api_project_annuel.Domain.entities.Post;
import com.esgi.api_project_annuel.Domain.entities.User;
import com.esgi.api_project_annuel.application.command.PostCommand;
import com.esgi.api_project_annuel.application.query.PostQuery;
import com.esgi.api_project_annuel.application.query.UserQuery;
import com.esgi.api_project_annuel.application.validation.PostValidationService;
import com.esgi.api_project_annuel.web.request.PostRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping
public class PostController {
    @Autowired
    private final PostCommand postCommand;

    @Autowired
    private final PostQuery postQuery;

    @Autowired
    private final UserQuery userQuery;

    private PostValidationService postValidationService;

    public PostController(PostCommand postCommand, PostQuery postQuery, UserQuery userQuery) {
        this.postCommand = postCommand;
        this.postQuery = postQuery;
        this.userQuery = userQuery;
    }

    @PostMapping("/post/create")
    public ResponseEntity<?> addPost(@RequestBody PostRequest postRequest){
        Post post = postCommand.create(postRequest);
        if(post == null)
            return new ResponseEntity<String>("Post not created",HttpStatus.NOT_ACCEPTABLE);
        else
            return new ResponseEntity<Post>(post, HttpStatus.CREATED);
    }


    @PostMapping("/post/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable int postId){
        Post post = postQuery.getById(postId);
        return new ResponseEntity<String>("Post not exist", HttpStatus.NOT_ACCEPTABLE);
    }
}
