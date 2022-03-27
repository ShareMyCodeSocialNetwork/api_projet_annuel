package com.esgi.api_project_annuel.application.query;

import com.esgi.api_project_annuel.Domain.entities.Post;
import com.esgi.api_project_annuel.Domain.entities.User;
import com.esgi.api_project_annuel.Domain.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostQuery {
    @Autowired
    PostRepository postRepository;

    public PostQuery(){}

    public List<Post> getAll(){
        return postRepository.findAll();
    }

    public Post getById(long postId){
        return postRepository.findById(postId);
    }

    public List<Post> getByUser(User user){
        return postRepository.findByUser(user);
    }
}
