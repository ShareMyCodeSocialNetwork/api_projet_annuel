package com.esgi.api_project_annuel.web.controller;

import com.esgi.api_project_annuel.Domain.entities.Comment;
import com.esgi.api_project_annuel.Domain.repository.CommentRepository;
import com.esgi.api_project_annuel.application.command.CommentCommand;
import com.esgi.api_project_annuel.application.query.CommentQuery;
import com.esgi.api_project_annuel.web.request.CommentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private final CommentCommand commentCommand;

    @Autowired
    private final CommentQuery commentQuery;

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
        var comment = commentCommand.create(commentRequest);

        if(comment != null)
            return new ResponseEntity<>(comment, HttpStatus.CREATED);
        else
            return new ResponseEntity<>("comment not created",HttpStatus.NOT_ACCEPTABLE);
    }
}
