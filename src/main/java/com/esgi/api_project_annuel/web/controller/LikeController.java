package com.esgi.api_project_annuel.web.controller;

import com.esgi.api_project_annuel.Domain.entities.Like;
import com.esgi.api_project_annuel.application.command.LikeCommand;
import com.esgi.api_project_annuel.application.query.LikeQuery;
import com.esgi.api_project_annuel.application.query.PostQuery;
import com.esgi.api_project_annuel.application.query.UserQuery;
import com.esgi.api_project_annuel.web.request.LikeRequest;
import com.esgi.api_project_annuel.web.response.LikeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/like")
public class LikeController {
    
    @Autowired
    LikeCommand likeCommand;
    
    @Autowired
    LikeQuery likeQuery;

    @Autowired
    UserQuery userQuery;

    @Autowired
    PostQuery postQuery;

    public LikeController(LikeCommand likeCommand, LikeQuery likeQuery) {
        this.likeCommand = likeCommand;
        this.likeQuery = likeQuery;
    }


    @RequestMapping("/create")
    public ResponseEntity<LikeResponse> addLike(@RequestBody LikeRequest likeRequest){
         var user = userQuery.getById(likeRequest.user_id);
         var post = postQuery.getById(likeRequest.post_id);
        var like = likeCommand.create(likeRequest, user, post);
        if(like == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(likeToLikeResponse(like), HttpStatus.OK);
    }


    @GetMapping("/")
    public ResponseEntity<List<LikeResponse>> getAll(){
        return new ResponseEntity<>(
                this.listLikeToListLikeResponse(likeQuery.getAll()),
                HttpStatus.OK);
    }

    @GetMapping("/{likeId}")
    public ResponseEntity<LikeResponse> getLikeById(@PathVariable int likeId){
        var like = likeQuery.getById(likeId);
        if(like == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(likeToLikeResponse(
                like),
                HttpStatus.OK
        );
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<LikeResponse>> getLikeByPost(@PathVariable int postId){
        var like = likeQuery.getByPost(postQuery.getById(postId));
        if(like == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(listLikeToListLikeResponse(
                like),
                HttpStatus.OK
        );
    }


    @DeleteMapping("/{likeId}")
    public ResponseEntity<?> deleteLike(@PathVariable int likeId){
        var like = likeQuery.getById(likeId);
        if(like == null)
            return new ResponseEntity<>(
                    "Like " + likeId + " not exist",
                    HttpStatus.BAD_REQUEST
            );
        likeCommand.delete(likeId);
        return new ResponseEntity<>(
                "Like " + likeId + " deleted",
                HttpStatus.OK
        );
    }






    private LikeResponse likeToLikeResponse(Like like){
        return new LikeResponse()
                .setId(like.getId())
                .setPost(like.getPost())
                .setUser(like.getUser());
    }

    private List<LikeResponse> listLikeToListLikeResponse(List<Like> likes){
        List<LikeResponse> likeResponses = new ArrayList<>();
        likes.forEach(like -> likeResponses.add(this.likeToLikeResponse(like)));
        return likeResponses;
    }
    
}
