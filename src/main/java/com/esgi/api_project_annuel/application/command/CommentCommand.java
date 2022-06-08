package com.esgi.api_project_annuel.application.command;

import com.esgi.api_project_annuel.Domain.entities.Comment;
import com.esgi.api_project_annuel.Domain.entities.Post;
import com.esgi.api_project_annuel.Domain.entities.User;
import com.esgi.api_project_annuel.Domain.repository.CommentRepository;
import com.esgi.api_project_annuel.application.validation.CommentValidationService;
import com.esgi.api_project_annuel.application.validation.PostValidationService;
import com.esgi.api_project_annuel.application.validation.UserValidationService;
import com.esgi.api_project_annuel.web.request.CommentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentCommand {
    @Autowired
    CommentRepository commentRepository;

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

    public Comment changeContent(int commentId, CommentRequest commentRequest){
        Optional<Comment> dbComment = Optional.ofNullable(commentRepository.findById(commentId));

        if(dbComment.isPresent()){
            dbComment.get().setContent(commentRequest.content);
            if(commentValidationService.isValid(dbComment.get()))
                return commentRepository.save(dbComment.get());
        }
        return null;
    }

    public void deleteAllUserComments(User user){
        Optional<List<Comment>> dbComments = Optional.ofNullable(commentRepository.findCommentsByUser(user));
        dbComments.ifPresent(comments->
                comments.forEach(comment -> {
                    comment.setPost(null);
                    comment.setUser(null);
                    commentRepository.save(comment);
                    commentRepository.delete(comment);
                })
        );
    }

    public void deleteCommentsInPost(Post post){
        Optional<List<Comment>> comments = Optional.ofNullable(commentRepository.findCommentsByPost(post));
        comments.ifPresent(comment -> commentRepository.deleteAll(comment));
    }

    public void delete(int commentId){
        Optional<Comment> commentToDelete = Optional.ofNullable(commentRepository.findById(commentId));
        commentToDelete.ifPresent(comment ->{
            comment.setUser(null);
            comment.setPost(null);
            commentRepository.save(comment);
            commentRepository.delete(comment);
        });
    }
}
