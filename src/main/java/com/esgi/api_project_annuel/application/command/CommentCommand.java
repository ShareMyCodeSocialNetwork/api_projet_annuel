package com.esgi.api_project_annuel.application.command;

import com.esgi.api_project_annuel.Domain.entities.Comment;
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
        var comment = new Comment();
        comment.setContent(comment.getContent());

        var post = postRepository.findById(commentRequest.post_id);
        if(!postValidationService.isValid(post))
            throw new RuntimeException("Invalid post");
        comment.setPost(post);

        var user = userRepository.findById(commentRequest.user_id);
        if(!userValidationService.isUserValid(user))
            throw new RuntimeException("Invalid user");

        if(!commentValidationService.isValid(comment))
            throw new RuntimeException("Invalid comment properties");
        return commentRepository.save(comment);
    }

    public Comment changeContent(int commentId, String content){
        Optional<Comment> dbComment = Optional.ofNullable(commentRepository.findById(commentId));

        if(dbComment.isEmpty())
            return null; //todo : error to do

        var comment = new Comment();
        comment.setContent(content);
        comment.setId(commentId);

        return commentRepository.save(comment);
    }

    public void delete(int commentId){
        var comment = commentRepository.findById(commentId);
        commentRepository.delete(comment);
    }
}
