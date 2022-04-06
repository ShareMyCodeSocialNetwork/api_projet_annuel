package com.esgi.api_project_annuel.application.command;

import com.esgi.api_project_annuel.Domain.entities.Comment;
import com.esgi.api_project_annuel.Domain.entities.Post;
import com.esgi.api_project_annuel.Domain.entities.User;
import com.esgi.api_project_annuel.Domain.repository.CommentRepository;
import com.esgi.api_project_annuel.Domain.repository.PostRepository;
import com.esgi.api_project_annuel.Domain.repository.UserRepository;
import com.esgi.api_project_annuel.application.validation.CommentValidationService;
import com.esgi.api_project_annuel.application.validation.PostValidationService;
import com.esgi.api_project_annuel.application.validation.UserValidationService;
import com.esgi.api_project_annuel.web.request.CommentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentCommand {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    PostValidationService postValidationService;
    UserValidationService userValidationService;
    CommentValidationService commentValidationService;

    public Comment create(CommentRequest commentRequest){
        Comment comment = new Comment();
        comment.setContent(comment.getContent());

        Post post = postRepository.findById(commentRequest.post_id);
        if(!postValidationService.isValid(post))
            throw new RuntimeException("Invalid post");
        comment.setPost(post);

        User user = userRepository.findById(commentRequest.user_id);
        if(!userValidationService.isUserValid(user))
            throw new RuntimeException("Invalid user");

        if(!commentValidationService.isValid(comment))
            throw new RuntimeException("Invalid comment properties");
        return commentRepository.save(comment);
    }

    public Comment update(int commentId, CommentRequest commentRequest){
        Optional<Comment> dbComment = Optional.ofNullable(commentRepository.findById(commentId));
        Comment comment = new Comment();
        comment.setContent(commentRequest.content);

        Post post = postRepository.findById(commentRequest.post_id);
        if(!postValidationService.isValid(post))
            throw new RuntimeException("invalid post");

        User user = userRepository.findById(commentRequest.user_id);
        if(!userValidationService.isUserValid(user))
            throw new RuntimeException("invalid user");

        return commentRepository.save(comment);
    }
}
