package com.esgi.api_project_annuel.web.controller;

import com.esgi.api_project_annuel.Domain.entities.Post;
import com.esgi.api_project_annuel.application.command.PostCommand;
import com.esgi.api_project_annuel.application.query.CodeQuery;
import com.esgi.api_project_annuel.application.query.PostQuery;
import com.esgi.api_project_annuel.application.query.UserQuery;
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
    private final CodeQuery codeQuery;

    @Autowired
    private final UserQuery userQuery;

    public PostController(PostCommand postCommand, PostQuery postQuery, CodeQuery codeQuery, UserQuery userQuery) {
        this.postCommand = postCommand;
        this.postQuery = postQuery;
        this.codeQuery = codeQuery;
        this.userQuery = userQuery;
    }

    @PostMapping("/create")
    public ResponseEntity<?> addPost(@RequestBody PostRequest postRequest){

        var user = userQuery.getById(postRequest.user_id);
        var code = codeQuery.getById(postRequest.code_id);
        var post = postCommand.create(postRequest, user, code);

        if(post == null)
            return new ResponseEntity<>("Post not created",HttpStatus.NOT_ACCEPTABLE);

        return new ResponseEntity<>(postToPostResponse(post), HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable int postId){
        var post = postQuery.getById(postId);
        if(post == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
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

    @PatchMapping("/update/{postId}")
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
                    HttpStatus.NOT_FOUND
            );
        postCommand.delete(postId);
        return new ResponseEntity<>(
                "Post " + postId + " deleted",
                HttpStatus.NO_CONTENT
        );
    }




    private PostResponse postToPostResponse(Post post){
        return new PostResponse()
                .setId(post.getId())
                .setUser(post.getUser())
                .setCode(post.getCode())
                .setContent(post.getContent());
    }

    private List<PostResponse> listPostToListPostResponse(List<Post> posts){
        List<PostResponse> postResponses = new ArrayList<>();
        posts.forEach(post -> postResponses.add(this.postToPostResponse(post)));
        return postResponses;
    }
}
