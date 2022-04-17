package com.esgi.api_project_annuel.web.controller;

import com.esgi.api_project_annuel.Domain.entities.Comment;
import com.esgi.api_project_annuel.Domain.entities.Post;
import com.esgi.api_project_annuel.Domain.repository.CommentRepository;
import com.esgi.api_project_annuel.application.command.CommentCommand;
import com.esgi.api_project_annuel.application.query.CommentQuery;
import com.esgi.api_project_annuel.application.query.PostQuery;
import com.esgi.api_project_annuel.application.query.UserQuery;
import com.esgi.api_project_annuel.web.request.CommentRequest;
import com.esgi.api_project_annuel.web.request.PostRequest;
import com.esgi.api_project_annuel.web.response.CommentResponse;
import com.esgi.api_project_annuel.web.response.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private final CommentCommand commentCommand;

    @Autowired
    private final CommentQuery commentQuery;

    @Autowired
    UserQuery userQuery;

    @Autowired
    PostQuery postQuery;

    public CommentController(CommentCommand commentCommand, CommentQuery commentQuery) {
        this.commentCommand = commentCommand;
        this.commentQuery = commentQuery;
    }

    /**
     * todo :
     *      - get all ?
     *      - getById
     *      - findByUser
     *      - findByPost
     *      - changeContent
     *      - delete
     */



    @PostMapping("/create")
    public ResponseEntity<?> createCommentOnPost(@RequestBody CommentRequest commentRequest){
        if(!isValidCommentRequest(commentRequest))
            return new ResponseEntity<>("Missing Properties", HttpStatus.BAD_REQUEST);

        var user = userQuery.getById(commentRequest.user_id);
        var post = postQuery.getById(commentRequest.post_id);

        var comment = commentToCommentResponse(
                commentCommand.create(commentRequest,
                post,
                user)
        );
        if(comment != null)
            return new ResponseEntity<>(comment, HttpStatus.CREATED);

        return new ResponseEntity<>("comment not created",HttpStatus.NOT_ACCEPTABLE);
    }






    private boolean isValidCommentRequest(CommentRequest commentRequest){
        return !( null == commentRequest.content || commentRequest.content.equals("") ||
                commentRequest.user_id <= 0 || commentRequest.post_id <= 0 );
    }

    private CommentResponse commentToCommentResponse(Comment comment){
        return new CommentResponse()
                .setId(comment.getId())
                .setContent(comment.getContent())
                .setUser_id(comment.getUser().getId())
                .setPost_id(comment.getPost().getId());
    }

    private List<CommentResponse> listCommentToListCommentResponse(List<Comment> comments){
        List<CommentResponse> commentResponses = new ArrayList<>();
        comments.forEach(post -> commentResponses.add(this.commentToCommentResponse(post)));
        return commentResponses;
    }
}
