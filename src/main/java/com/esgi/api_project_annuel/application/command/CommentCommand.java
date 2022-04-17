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

    PostValidationService postValidationService = new PostValidationService();
    UserValidationService userValidationService = new UserValidationService();
    CommentValidationService commentValidationService = new CommentValidationService();

    public Comment create(CommentRequest commentRequest, Post post, User user){
        var comment = new Comment();
        comment.setContent(commentRequest.content);

        if(!postValidationService.isValid(post))
            return null;
        comment.setPost(post);

        if(!userValidationService.isUserValid(user))
            return null;
        comment.setUser(user);

        if(!commentValidationService.isValid(comment))
            return null;
        return commentRepository.save(comment);
    }

    public Comment changeContent(int commentId, String content){
        Optional<Comment> dbComment = Optional.ofNullable(commentRepository.findById(commentId));

        if(dbComment.isEmpty())
            return null;

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
