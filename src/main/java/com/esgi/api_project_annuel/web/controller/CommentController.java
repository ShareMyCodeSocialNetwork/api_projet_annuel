package com.esgi.api_project_annuel.web.controller;

import com.esgi.api_project_annuel.Domain.entities.Comment;
import com.esgi.api_project_annuel.application.command.CommentCommand;
import com.esgi.api_project_annuel.application.query.CommentQuery;
import com.esgi.api_project_annuel.application.query.PostQuery;
import com.esgi.api_project_annuel.application.query.UserQuery;
import com.esgi.api_project_annuel.web.request.CommentRequest;
import com.esgi.api_project_annuel.web.response.CommentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
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



    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CommentResponse>> getByUser(@PathVariable int userId){
        return new ResponseEntity<>(
                listCommentToListCommentResponse(
                        commentQuery.findByUser(
                                userQuery.getById(userId)
                        )
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResponse>> getByPost(@PathVariable int postId){
        return new ResponseEntity<>(
                listCommentToListCommentResponse(
                        commentQuery.findByPost(
                                postQuery.getById(postId)
                        )
                ),
                HttpStatus.OK
        );
    }

    @PatchMapping("/update/{commentId}")
    public ResponseEntity<CommentResponse> changeContent(@PathVariable int commentId, @RequestBody CommentRequest commentRequest){
        var comment = commentQuery.getById(commentId);
        if(comment == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        var updatedComment = commentCommand.changeContent(commentId, commentRequest);
        if(updatedComment == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(commentToCommentResponse(updatedComment),HttpStatus.OK);
    }


    @GetMapping(value = "/", produces = { MimeTypeUtils.APPLICATION_JSON_VALUE }, headers = "Accept=application/json")
    public ResponseEntity<List<CommentResponse>> getAll(){
        return new ResponseEntity<>(
                listCommentToListCommentResponse(commentQuery.getAll()),
                HttpStatus.OK
        );
    }


    @GetMapping(value = "/{commentId}", produces = { MimeTypeUtils.APPLICATION_JSON_VALUE }, headers = "Accept=application/json")
    public ResponseEntity<CommentResponse> getById(@PathVariable int commentId) {
        var comment = commentQuery.getById(commentId);
        if (comment == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(commentToCommentResponse(
                comment),
                HttpStatus.OK
        );
    }



    @PostMapping("/create")
    public ResponseEntity<?> createCommentOnPost(@RequestBody CommentRequest commentRequest){

        var user = userQuery.getById(commentRequest.user_id);
        var post = postQuery.getById(commentRequest.post_id);

        var comment = commentCommand.create(commentRequest, post, user);
        if(comment != null)
            return new ResponseEntity<>(commentToCommentResponse(comment), HttpStatus.CREATED);

        return new ResponseEntity<>("Comment not created",HttpStatus.NOT_ACCEPTABLE);
    }


    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable int commentId){
        if(null == commentQuery.getById(commentId))
            return new ResponseEntity<>("Comment does not exist", HttpStatus.NOT_FOUND);
        commentCommand.delete(commentId);
        return new ResponseEntity<>("Comment deleted",HttpStatus.NO_CONTENT);
    }




    private CommentResponse commentToCommentResponse(Comment comment){
        return new CommentResponse()
                .setId(comment.getId())
                .setContent(comment.getContent())
                .setUser(comment.getUser())
                .setPost(comment.getPost());
    }

    private List<CommentResponse> listCommentToListCommentResponse(List<Comment> comments){
        List<CommentResponse> commentResponses = new ArrayList<>();
        comments.forEach(comment -> commentResponses.add(this.commentToCommentResponse(comment)));
        return commentResponses;
    }
}
