package com.esgi.api_project_annuel.application.query;

import com.esgi.api_project_annuel.Domain.entities.Like;
import com.esgi.api_project_annuel.Domain.entities.Post;
import com.esgi.api_project_annuel.Domain.entities.User;
import com.esgi.api_project_annuel.Domain.repository.LikeRepository;
import com.esgi.api_project_annuel.Domain.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostQuery {
    @Autowired
    PostRepository postRepository;

    @Autowired
    LikeRepository likeRepository;


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

    public Like getLikes(int likeId){
        return likeRepository.findById(likeId);
    }

}
