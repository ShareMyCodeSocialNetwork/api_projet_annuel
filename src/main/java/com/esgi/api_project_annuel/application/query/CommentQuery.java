package com.esgi.api_project_annuel.application.query;

import com.esgi.api_project_annuel.Domain.entities.Comment;
import com.esgi.api_project_annuel.Domain.entities.Post;
import com.esgi.api_project_annuel.Domain.entities.User;
import com.esgi.api_project_annuel.Domain.repository.CommentRepository;
import com.esgi.api_project_annuel.Domain.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentQuery {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    public List<Comment> getAll(){
        return commentRepository.findAll();
    }

    public Comment getById(int commentId){
        return commentRepository.findById(commentId);
    }

    public List<Comment> findByUser(User user){
        return commentRepository.findCommentsByUser(user);
    }

    /*public List<Comment> findByPost(Post post){
        return commentRepository.findCommentsByPost(post);
    }*/
}
