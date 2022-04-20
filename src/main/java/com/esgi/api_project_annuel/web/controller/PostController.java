package com.esgi.api_project_annuel.web.controller;

import com.esgi.api_project_annuel.Domain.entities.Post;
import com.esgi.api_project_annuel.application.command.PostCommand;
import com.esgi.api_project_annuel.application.query.PostQuery;
import com.esgi.api_project_annuel.application.query.UserQuery;
import com.esgi.api_project_annuel.application.validation.PostValidationService;
import com.esgi.api_project_annuel.web.request.PostRequest;
import com.esgi.api_project_annuel.web.response.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/post")
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

    @PostMapping("/create")
    public ResponseEntity<?> addPost(@RequestBody PostRequest postRequest){
        if(!isValidPostRequest(postRequest))
            return new ResponseEntity<>("missing properties", HttpStatus.BAD_REQUEST);

        var user = userQuery.getById(postRequest.user_id);
        var post = postCommand.create(postRequest, user);

        if(post == null)
            return new ResponseEntity<>("Post not created",HttpStatus.NOT_ACCEPTABLE);

        return new ResponseEntity<>(postToPostResponse(post), HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable int postId){
        var post = postQuery.getById(postId);
        if(post == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(postToPostResponse(
                post),
                HttpStatus.OK
        );
    }

    @GetMapping("/")
    public ResponseEntity<List<PostResponse>> getAll(){
        return new ResponseEntity<>(
                this.listPostToListPostResponse(postQuery.getAll()),
                HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostResponse>> getAllUserPosts(@PathVariable int userId){
        return new ResponseEntity<>(
                this.listPostToListPostResponse(postQuery.getByUser(userId)),
                HttpStatus.OK
        );
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<?> changeContent(@PathVariable int postId,@RequestBody PostRequest postRequest){
        var post = postCommand.changeContent(postRequest,postId);
        if(post == null)
            return new ResponseEntity<>("Invalid properties", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(postToPostResponse(post), HttpStatus.OK);
    }


    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable int postId){
        var post = postQuery.getById(postId);
        if(post == null)
            return new ResponseEntity<>(
                    "Post " + postId + " not exist",
                    HttpStatus.BAD_REQUEST
            );
        postCommand.delete(postId);
        return new ResponseEntity<>(
                "Post " + postId + " deleted",
                HttpStatus.BAD_REQUEST
        );
    }







    private boolean isValidPostRequest(PostRequest postRequest){
        return !( null == postRequest.content || postRequest.content.equals("") || postRequest.user_id <= 0 );
    }

    private PostResponse postToPostResponse(Post post){
        return new PostResponse()
                .setId(post.getId())
                .setUser(post.getUser())
                .setContent(post.getContent());
    }

    private List<PostResponse> listPostToListPostResponse(List<Post> posts){
        List<PostResponse> postResponses = new ArrayList<>();
        posts.forEach(post -> postResponses.add(this.postToPostResponse(post)));
        return postResponses;
    }
}
