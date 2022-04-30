package com.esgi.api_project_annuel.web.controller;

import com.esgi.api_project_annuel.Domain.entities.Follow;
import com.esgi.api_project_annuel.application.command.FollowCommand;
import com.esgi.api_project_annuel.application.query.FollowQuery;
import com.esgi.api_project_annuel.application.query.UserQuery;
import com.esgi.api_project_annuel.web.request.FollowRequest;
import com.esgi.api_project_annuel.web.response.FollowResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/follow")
public class FollowController {
    @Autowired
    private final FollowCommand followCommand;

    @Autowired
    private final FollowQuery followQuery;

    @Autowired
    UserQuery userQuery;

    public FollowController(FollowCommand followCommand, FollowQuery demandQuery){
        this.followCommand = followCommand;
        this.followQuery = demandQuery;
    }

    //todo, to test
    @PostMapping("/create")
    public ResponseEntity<FollowResponse> addFollow(@RequestBody FollowRequest followRequest) {
        var follower = userQuery.getById(followRequest.followerUserId);
        var followed = userQuery.getById(followRequest.followedUserId);
        var follow = followCommand.create(followed,follower);
        if(follow != null)
            return new ResponseEntity<>(followToFollowResponse(follow), HttpStatus.CREATED);
        return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);

    }

    //todo  A FINIR TOUT CE QUI EST EN DESSOUS
    @GetMapping(value = "/", produces = { MimeTypeUtils.APPLICATION_JSON_VALUE }, headers = "Accept=application/json")
    public ResponseEntity<List<FollowResponse>> getFollowAll(){
        return new ResponseEntity<>(
                listFollowToListFollowResponse(followQuery.getAll()),
                HttpStatus.OK);
    }

    @GetMapping("/followers/{followedId}")
    public ResponseEntity<List<FollowResponse>> getFollowers(@PathVariable int followedId){
        var followers = followQuery.getAllByFollowedUser(userQuery.getById(followedId));
        if(followers == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(listFollowToListFollowResponse(
                followers),
                HttpStatus.OK
        );
    }

    @GetMapping("/followed/{followerId}")
    public ResponseEntity<List<FollowResponse>> getFollowed(@PathVariable int followerId){
        var followed = followQuery.getAllByFollowerUser(userQuery.getById(followerId));
        if(followed == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(listFollowToListFollowResponse(
                followed),
                HttpStatus.OK
        );
    }

    @GetMapping("/{followId}")
    public ResponseEntity<FollowResponse> getFollowById(@PathVariable int followId) {
        var follow = followQuery.getById(followId);
        if(follow == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(followToFollowResponse(
                follow),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{followId}")
    public ResponseEntity<String> deleteFollow(@PathVariable int followId) {
        var follow = followQuery.getById(followId);
        if(follow == null)
            return new ResponseEntity<>(
                    "Role " + followId + " not exist",
                    HttpStatus.BAD_REQUEST
            );
        followCommand.delete(followId);
        return new ResponseEntity<>(
                "follow " + followId + " deleted",
                HttpStatus.NO_CONTENT
        );
    }



    private FollowResponse followToFollowResponse(Follow follow){
        return new FollowResponse()
                .setFollowed(follow.getFollowedUser())
                .setFollower(follow.getFollowerUser());
    }

    private List<FollowResponse> listFollowToListFollowResponse(List<Follow> follows){
        List<FollowResponse> followResponses = new ArrayList<>();
        follows.forEach(follow -> followResponses.add(followToFollowResponse(follow)));
        return followResponses;
    }
}