package com.esgi.api_project_annuel.application.query;

import com.esgi.api_project_annuel.Domain.entities.Like;
import com.esgi.api_project_annuel.Domain.entities.Post;
import com.esgi.api_project_annuel.Domain.entities.User;
import com.esgi.api_project_annuel.Domain.repository.LikeRepository;
import com.esgi.api_project_annuel.Domain.repository.PostRepository;
import com.esgi.api_project_annuel.Domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostQuery {
    @Autowired
    PostRepository postRepository;

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    UserRepository userRepository;


    public PostQuery(){}

    public List<Post> getAll(){
        return postRepository.findAll();
    }

    public Post getById(int postId){
        return postRepository.findById(postId);
    }

    public List<Post> getByUser(int userId){
        return postRepository.findByUser(userRepository.getById(userId));
    }

    public List<Like> getLikes(int postId){
        var post = postRepository.findById(postId);
        return likeRepository.findAllByPost(post);
    }

    public List<Post> getPostsByUserFollowed(List<User> followed){
        List<Post> posts = new ArrayList<>();
        followed.forEach(user ->
            posts.addAll(postRepository.findByUser(user))
        );
        return posts;
    }

}
